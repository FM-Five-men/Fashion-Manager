package fashionmanager.baek.develop.service;


import fashionmanager.baek.develop.dto.*;
import fashionmanager.baek.develop.entity.PhotoEntity;
import fashionmanager.baek.develop.entity.ReviewPostEntity;
import fashionmanager.baek.develop.entity.ReviewPostItemEntity;
import fashionmanager.baek.develop.entity.pk.ReviewPostItemPK;
import fashionmanager.baek.develop.mapper.ReviewPostMapper;
import fashionmanager.baek.develop.repository.PhotoRepository;
import fashionmanager.baek.develop.repository.ReviewItemRepository;
import fashionmanager.baek.develop.repository.ReviewPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReviewPostService {
    private final ReviewPostRepository reviewPostRepository;
    private final ReviewItemRepository reviewItemRepository;
    private final PhotoRepository photoRepository;
    private final ReviewPostMapper reviewPostMapper;
    private String postUploadPath = "C:\\uploadFiles\\review";
    private String reviewItemUploadPath = "C:\\uploadFiles\\review_items";

    @Autowired
    public ReviewPostService(ReviewPostRepository reviewPostRepository,
                             ReviewItemRepository reviewItemRepository, PhotoRepository photoRepository, ReviewPostMapper reviewPostMapper) {
        this.reviewPostRepository = reviewPostRepository;
        this.reviewItemRepository = reviewItemRepository;
        this.photoRepository = photoRepository;
        this.reviewPostMapper = reviewPostMapper;
    }

    public List<SelectAllReviewPostDTO> getPostList() {
        return reviewPostMapper.findAll();
    }

    public SelectDetailReviewPostDTO getDetailPost(int postNum) {
        SelectDetailReviewPostDTO postDetail = reviewPostMapper.findById(postNum);
        if (postDetail == null) {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.");
        }
        return postDetail;
    }

    @Transactional
    public ReviewRegistResponseDTO registPost(ReviewRegistRequestDTO newPost, List<MultipartFile> postFiles,
                                        List<MultipartFile> itemFiles) {
        /* 설명. 1. fashion_post table에 게시글 등록 */
        ReviewPostEntity reviewPostEntity = changeToRegistPost(newPost);
        ReviewPostEntity registReviewPost = reviewPostRepository.save(reviewPostEntity);
        int postNum = registReviewPost.getNum();

        /* 설명. 2. review_item table에 아이템 등록 */
        for (Integer itemNums : newPost.getItems()) {
            ReviewPostItemPK reviewPostItemPK = new ReviewPostItemPK(postNum, itemNums);
            ReviewPostItemEntity reviewPostItemEntity = new ReviewPostItemEntity(reviewPostItemPK);
            reviewItemRepository.save(reviewPostItemEntity); // 반복문 안에서 매번 저장
        }

        /* 설명. 3. photo table에 사진 등록, 사진 카테고리 번호 1 = 패션 게시물 */
        if (postFiles != null && !postFiles.isEmpty()) {
            File uploadDir = new File(postUploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // 경로에 해당하는 폴더가 없으면 생성해줌
            }
            for (MultipartFile imageFile : postFiles) {
                String originalFileName = imageFile.getOriginalFilename();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String savedFileName = UUID.randomUUID().toString() + extension;

                File targetFile = new File(postUploadPath + File.separator + savedFileName);
                try {
                    imageFile.transferTo(targetFile);
                } catch (IOException e) {
                    throw new RuntimeException("파일 저장에 실패했습니다", e);
                }
                PhotoEntity photoEntity = new PhotoEntity();
                photoEntity.setName(savedFileName); // 고유한 이름으로 저장
                photoEntity.setPath(postUploadPath);
                photoEntity.setPostNum(postNum);    // postNum과 CategoryNum 지정
                photoEntity.setPhotoCategoryNum(2); // 후기 게시물 사진은 2
                photoRepository.save(photoEntity);
            }
        }
        /* 설명. 3-2. 패션 아이템 사진 등록, 사진 카테고리 번호 4 = 패션 아이템 */
        if (itemFiles != null && !itemFiles.isEmpty()) {
            File uploadDir = new File(reviewItemUploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // 경로에 해당하는 폴더가 없으면 생성해줌
            }
            for (MultipartFile imageFile : itemFiles) {
                String originalFileName = imageFile.getOriginalFilename();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String savedFileName = UUID.randomUUID().toString() + extension;

                File targetFile = new File(reviewItemUploadPath + File.separator + savedFileName);
                try {
                    imageFile.transferTo(targetFile);
                } catch (IOException e) {
                    throw new RuntimeException("파일 저장에 실패했습니다", e);
                }
                PhotoEntity photoEntity = new PhotoEntity();
                photoEntity.setName(savedFileName); // 고유한 이름으로 저장
                photoEntity.setPath(reviewItemUploadPath);
                photoEntity.setPostNum(postNum);    // postNum과 CategoryNum 저장
                photoEntity.setPhotoCategoryNum(5); // 후기 아이템 사진은 5
                photoRepository.save(photoEntity);
            }
        }

        ReviewRegistResponseDTO response = new ReviewRegistResponseDTO();
        response.setNum(postNum);
        response.setTitle(registReviewPost.getTitle());
        response.setContent(registReviewPost.getContent());
        response.setItems(newPost.getItems());
        response.setMemberNum(registReviewPost.getMemberNum());
        return response;
    }

    private ReviewPostEntity changeToRegistPost(ReviewRegistRequestDTO newPost) {
        ReviewPostEntity reviewPostEntity = new ReviewPostEntity();
        reviewPostEntity.setTitle(newPost.getTitle());
        reviewPostEntity.setContent(newPost.getContent());
        reviewPostEntity.setGood(0);
        reviewPostEntity.setCheer(0);
        reviewPostEntity.setMemberNum(newPost.getMemberNum());
        reviewPostEntity.setReviewCategoryNum(newPost.getReviewCategoryNum());
        return reviewPostEntity;
    }


    @Transactional
    public ReviewModifyResponseDTO modifyPost(int postNum, ReviewModifyRequestDTO updatePost,
                                        List<MultipartFile> postFiles, List<MultipartFile> itemFiles) {
        ReviewPostEntity reviewPostEntity = reviewPostRepository.findById(postNum)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. id=" + postNum));

        reviewPostEntity.setTitle(updatePost.getTitle());
        reviewPostEntity.setContent(updatePost.getContent());
        reviewPostEntity.setReviewCategoryNum(updatePost.getReviewCategoryNum());

        List<Integer> updateItems = updateItems(postNum, updatePost.getItems());

        updatePhotos(reviewPostEntity,this.postUploadPath, postFiles, 2);
        updatePhotos(reviewPostEntity,this.reviewItemUploadPath, itemFiles, 5);

        ReviewModifyResponseDTO response = new ReviewModifyResponseDTO();
        response.setNum(postNum);
        response.setTitle(reviewPostEntity.getTitle());
        response.setContent(reviewPostEntity.getContent());
        response.setMemberNum(reviewPostEntity.getMemberNum());
        response.setItems(updateItems);
        response.setReviewCategoryNum(reviewPostEntity.getReviewCategoryNum());
        return response;
    }

    private void updatePhotos(ReviewPostEntity post, String uploadPath, List<MultipartFile> newImageFiles, int categoryNum) {
        int postNum = post.getNum();
        List<PhotoEntity> photosToUpdate = photoRepository.findAllByPostNumAndPhotoCategoryNum(postNum, categoryNum);
        for (PhotoEntity photo : photosToUpdate) {
            File fileToDelete = new File(photo.getPath() + File.separator + photo.getName());
            boolean deleted = fileToDelete.delete();
            if (!deleted) {
                log.info("수정 중 사진 파일 삭제에 실패했습니다. {}", fileToDelete.getPath());  // 삭제 실패했다면 log로 남김
            }
        }
        photoRepository.deleteAll(photosToUpdate);

        if (newImageFiles != null && !newImageFiles.isEmpty()) {
            saveNewPhotos(post, uploadPath, newImageFiles, categoryNum);
        }
    }

    private void saveNewPhotos(ReviewPostEntity post, String uploadPath,
                               List<MultipartFile> imageFiles, int categoryNum) {
        int postNum = post.getNum();
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) { uploadDir.mkdirs(); }
        for (MultipartFile imageFile : imageFiles) {
            String originalFileName = imageFile.getOriginalFilename();
            String extension = "";
            if (originalFileName != null) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String savedFileName = UUID.randomUUID().toString() + extension;

            File targetFile = new File(uploadPath + File.separator + savedFileName);
            try {
                imageFile.transferTo(targetFile);
            } catch (IOException e) {
                throw new RuntimeException("새로운 파일 저장에 실패했습니다", e);
            }

            PhotoEntity newPhoto = new PhotoEntity();
            newPhoto.setName(savedFileName);
            newPhoto.setPath(uploadPath);
            newPhoto.setPostNum(postNum);
            newPhoto.setPhotoCategoryNum(categoryNum); // 해당 카테고리 넘버
            photoRepository.save(newPhoto);
        }
    }

    private List<Integer> updateItems(int postNum, List<Integer> newItemId) {
        List<Integer> currentItemsIds = reviewItemRepository.findAllByReviewPostItemPK_PostNum(postNum)
                .stream()
                .map(item -> item.getReviewPostItemPK().getItemNum())
                .collect(Collectors.toList());

        List<Integer> itemsToRemove = currentItemsIds.stream()
                .filter(id -> !newItemId.contains(id))
                .collect(Collectors.toList());
        if(!itemsToRemove.isEmpty()) {
            reviewItemRepository.deleteAllByReviewPostItemPK_PostNumAndReviewPostItemPK_ItemNumIn(postNum, itemsToRemove);
        }

        List<Integer> itemsToAdd = newItemId.stream()
                .filter(id -> !currentItemsIds.contains(id))
                .collect(Collectors.toList());
        for (Integer itemNum : itemsToAdd) {
            ReviewPostItemPK pk = new ReviewPostItemPK(postNum, itemNum);
            reviewItemRepository.save(new ReviewPostItemEntity(pk));
        }
        return newItemId;
    }

    @Transactional
    public void deletePost(int postNum) {
        ReviewPostEntity reviewToDelete = reviewPostRepository.findById(postNum)
                .orElseThrow(() ->new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        deleteItems(postNum);

        List<PhotoEntity> photosToDelete = photoRepository.findAllByPostNumAndPhotoCategoryNum(postNum, 2);
        photosToDelete.addAll(photoRepository.findAllByPostNumAndPhotoCategoryNum(postNum, 5));
        for (PhotoEntity photo : photosToDelete) {
            File file = new File(photo.getPath() + File.separator + photo.getName());
            if(file.exists()) {
                boolean deleted = file.delete();
                if (!deleted) {
                    log.info("파일 삭제 중 이미지 파일 삭제에 실패했습니다");
                }
            }
        }
        photoRepository.deleteAll(photosToDelete);

        reviewPostRepository.deleteById(postNum);
    }

    private void deleteItems(int postNum) {
        reviewItemRepository.deleteAllByReviewPostItemPK_PostNum(postNum);
    }
}
