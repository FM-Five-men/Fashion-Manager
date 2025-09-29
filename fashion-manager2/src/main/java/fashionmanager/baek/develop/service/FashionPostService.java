package fashionmanager.baek.develop.service;

import fashionmanager.baek.develop.dto.*;
import fashionmanager.baek.develop.entity.FashionHashTagEntity;
import fashionmanager.baek.develop.entity.FashionPostEntity;
import fashionmanager.baek.develop.entity.FashionPostItemEntity;
import fashionmanager.baek.develop.entity.PhotoEntity;
import fashionmanager.baek.develop.entity.pk.FashionHashTagPK;
import fashionmanager.baek.develop.entity.pk.FashionPostItemPK;
import fashionmanager.baek.develop.mapper.FashionPostMapper;
import fashionmanager.baek.develop.repository.FashionHashRepository;
import fashionmanager.baek.develop.repository.FashionItemRepository;
import fashionmanager.baek.develop.repository.FashionPostRepository;
import fashionmanager.baek.develop.repository.PhotoRepository;
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

@Service
@Slf4j
public class FashionPostService {
    private final FashionPostRepository fashionPostRepository;
    private final FashionHashRepository fashionHashRepository;
    private final FashionItemRepository fashionItemRepository;
    private final PhotoRepository photoRepository;
    private final FashionPostMapper fashionPostMapper;
    private String postUploadPath = "C:\\uploadFiles\\fashion";
    private String fashionItemsUploadPath = "C:\\uploadFiles\\fashion_items";



    @Autowired
    public FashionPostService(FashionPostRepository fashionPostRepository, FashionHashRepository fashionHashRepository,
                              FashionItemRepository fashionItemRepository, PhotoRepository photoRepository,
                              FashionPostMapper fashionPostMapper) {
        this.fashionPostRepository = fashionPostRepository;
        this.fashionHashRepository = fashionHashRepository;
        this.fashionItemRepository = fashionItemRepository;
        this.photoRepository = photoRepository;
        this.fashionPostMapper = fashionPostMapper;
    }

    public List<SelectAllFashionPostDTO> getPostList() {
        return fashionPostMapper.findAll();
    }

    public SelectDetailFashionPostDTO getDetailPost(int postNum) {
        SelectDetailFashionPostDTO postDetail = fashionPostMapper.findById(postNum);
        if (postDetail == null) {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.");
        }
        int good = postDetail.getGood();
        int cheer = postDetail.getCheer();
        double temp = 0.0;
        if(good + cheer > 0) {
            temp = ((double) good / (good + cheer)) * 100.0;
        }
        postDetail.setTemp(temp);
        return postDetail;
    }

    @Transactional
    public FashionRegistResponseDTO registPost(FashionRegistRequestDTO newPost, List<MultipartFile> postFiles,
                                        List<MultipartFile> itemFiles) {
        /* 설명. 1. fashion_post table에 게시글 등록 */
        if (newPost.getTitle() == null || newPost.getTitle().isBlank()) {
            throw new IllegalArgumentException("제목을 입력해주세요");
        }
        FashionPostEntity fashionPostEntity = changeToRegistPost(newPost);
        FashionPostEntity registFashionPost = fashionPostRepository.save(fashionPostEntity);
        int postNum = registFashionPost.getNum();

        /* 설명. 2. fashion_hash table에 해시태그 등록 */
        for (Integer tagNums : newPost.getHashtag()) {
            FashionHashTagPK fashionHashTagPK = new FashionHashTagPK(postNum, tagNums);
            FashionHashTagEntity fashionHashTagEntity = new FashionHashTagEntity(fashionHashTagPK);
            fashionHashRepository.save(fashionHashTagEntity);
        }
        /* 설명. 3-1. photo table에 게시물 사진 등록, 사진 카테고리 번호 1 = 패션 게시물 */
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
                photoEntity.setPhotoCategoryNum(1); // 패션 게시물 사진은 1
                photoRepository.save(photoEntity);
            }
        }
        /* 설명. 3-2. 패션 아이템 사진 등록, 사진 카테고리 번호 4 = 패션 아이템 */
        if (itemFiles != null && !itemFiles.isEmpty()) {
            File uploadDir = new File(fashionItemsUploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // 경로에 해당하는 폴더가 없으면 생성해줌
            }
            for (MultipartFile imageFile : itemFiles) {
                String originalFileName = imageFile.getOriginalFilename();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String savedFileName = UUID.randomUUID().toString() + extension;

                File targetFile = new File(fashionItemsUploadPath + File.separator + savedFileName);
                try {
                    imageFile.transferTo(targetFile);
                } catch (IOException e) {
                    throw new RuntimeException("파일 저장에 실패했습니다", e);
                }
                PhotoEntity photoEntity = new PhotoEntity();
                photoEntity.setName(savedFileName); // 고유한 이름으로 저장
                photoEntity.setPath(fashionItemsUploadPath);
                photoEntity.setPostNum(postNum);    // postNum과 CategoryNum 저장
                photoEntity.setPhotoCategoryNum(4); // 패션 아이템 사진은 4
                photoRepository.save(photoEntity);
            }
        }

        /* 설명. 4. fashion_item table에 아이템 등록 */
        for (Integer itemNums : newPost.getItems()) {
            FashionPostItemPK fashionPostItemPK = new FashionPostItemPK(postNum, itemNums);
            FashionPostItemEntity fashionPostItemEntity = new FashionPostItemEntity(fashionPostItemPK);
            fashionItemRepository.save(fashionPostItemEntity); // 반복문 안에서 매번 저장
        }

        /* 설명. 5. responseDTO 생성 */
        FashionRegistResponseDTO response = new FashionRegistResponseDTO();
        response.setNum(postNum);
        response.setTitle(registFashionPost.getTitle());
        response.setContent(registFashionPost.getContent());
        response.setHashtag(newPost.getHashtag());
        response.setItems(newPost.getItems());
        response.setMember_num(registFashionPost.getMember_num());
        return response;
    }

    private FashionPostEntity changeToRegistPost(FashionRegistRequestDTO newPost) {
        FashionPostEntity fashionPostEntity = new FashionPostEntity();
        fashionPostEntity.setTitle(newPost.getTitle());
        fashionPostEntity.setContent(newPost.getContent());
        fashionPostEntity.setGood(0);
        fashionPostEntity.setCheer(0);
        fashionPostEntity.setMember_num(newPost.getMemberNum());
        return fashionPostEntity;
    }


    @Transactional
    public FashionModifyResponseDTO modifyPost(int postNum, FashionModifyRequestDTO updatePost,
                                        List<MultipartFile> postFiles, List<MultipartFile> itemFiles) {
        /* 설명. 1. 수정할 게시글이 존재하는지 확인 */
        FashionPostEntity fashionPostEntity = fashionPostRepository.findById(postNum)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. id=" + postNum));

        /* 2. 게시글의 기본 정보(제목, 내용) 수정 */
        fashionPostEntity.setTitle(updatePost.getTitle());
        fashionPostEntity.setContent(updatePost.getContent());

        /* 3. 해시태그 업데이트 (비교 후 변경분만 반영) */
        List<Integer> updateTags = updateHashtags(postNum, updatePost.getHashtag());

        /* 4. 아이템 업데이트 (비교 후 변경분만 반영) */
        List<Integer> updateItems = updateItems(postNum, updatePost.getItems());

        /* 5. 사진 업데이트 (파일 처리는 기존의 'Delete & Insert' 방식 유지) */
        updatePhotos(fashionPostEntity,this.postUploadPath, postFiles, 1); // 게시물 사진 업데이트
        updatePhotos(fashionPostEntity,this.fashionItemsUploadPath, itemFiles, 4); // 패션 아이템 사진 업데이트

        /* 6. 최종 응답 DTO 생성 */
        FashionModifyResponseDTO response = new FashionModifyResponseDTO();
        response.setNum(postNum);
        response.setTitle(fashionPostEntity.getTitle());
        response.setContent(fashionPostEntity.getContent());
        response.setMember_num(fashionPostEntity.getMember_num());
        response.setHashtag(updateTags);
        response.setItems(updateItems);
        return response;
    }

    private List<Integer> updateHashtags(int postNum, List<Integer> newHashTagsId) {
        /* 설명. 1. DB에서 현재 해시태그 ID 목록 조회 */
        List<Integer> currentHashTagIds = fashionHashRepository.findAllByFashionHashTagPK_PostNum(postNum)
                .stream()
                .map(tag -> tag.getFashionHashTagPK().getTagNum())
                .collect(Collectors.toList());

        /* 설명. 2. 삭제할 해시태그 계산 */
        List<Integer> tagsToRemove = currentHashTagIds.stream()
                .filter(id -> !newHashTagsId.contains(id))
                .collect(Collectors.toList());
        if (!tagsToRemove.isEmpty()) {
            fashionHashRepository.deleteAllByFashionHashTagPK_PostNumAndFashionHashTagPK_TagNumIn(postNum, tagsToRemove);
        }

        /* 설명. 3. 추가할 해시태그 계산 */
        List<Integer> tagsToAdd = newHashTagsId.stream()
                .filter(id -> !currentHashTagIds.contains(id))
                .collect(Collectors.toList());
        for (Integer tagNum : tagsToAdd) {
            FashionHashTagPK pk = new FashionHashTagPK(postNum, tagNum);
            fashionHashRepository.save(new FashionHashTagEntity(pk));
        }
        return newHashTagsId;
    }

    private List<Integer> updateItems(int postNum, List<Integer> newItemId) {
        /* 설명. 1. DB에서 현재 아이템 ID 목록 조회 */
        List<Integer> currentItemIds = fashionItemRepository.findAllByFashionPostItemPK_PostNum(postNum)
                .stream()
                .map(item -> item.getFashionPostItemPK().getItemNum())
                .collect(Collectors.toList());

        /* 설명. 2. 삭제할 아이템 계산 */
        List<Integer> itemsToRemove = currentItemIds.stream()
                .filter(id -> !newItemId.contains(id))
                .collect(Collectors.toList());
        if (!itemsToRemove.isEmpty()) {
            fashionItemRepository.deleteAllByFashionPostItemPK_PostNumAndFashionPostItemPK_ItemNumIn(postNum, itemsToRemove);
        }

        /* 설명. 3. 추가할 아이템 계산 */
        List<Integer> itemsToAdd = newItemId.stream()
                .filter(id -> !currentItemIds.contains(id))
                .collect(Collectors.toList());
        for (Integer itemNum : itemsToAdd) {
            FashionPostItemPK pk = new FashionPostItemPK(postNum, itemNum);
            fashionItemRepository.save(new FashionPostItemEntity(pk));
        }
        return newItemId;
    }

    private void updatePhotos(FashionPostEntity post,String uploadPath, List<MultipartFile> newImageFiles, int categoryNum) {
        /* 설명. 1. 기존 사진 파일 및 DB 정보 삭제 */
        int postNum = post.getNum();
        List<PhotoEntity> photosToUpdate = photoRepository.findAllByPostNumAndPhotoCategoryNum(postNum, categoryNum);
        for (PhotoEntity photo : photosToUpdate) {
            File fileToDelete = new File(photo.getPath() + File.separator + photo.getName());
            if (fileToDelete.exists()) {
                boolean deleted = fileToDelete.delete();
                if (!deleted) {
                    log.info("수정 중 사진 파일 삭제에 실패했습니다. {}", fileToDelete.getPath());  // 삭제 실패했다면 log로 남김
                }
            }

        }
        photoRepository.deleteAll(photosToUpdate);

        /* 설명. 2. 새로운 사진 파일 추가 */
        if (newImageFiles != null && !newImageFiles.isEmpty()) {
            saveNewPhotos(post, uploadPath, newImageFiles, categoryNum);
        }

    }

    private void saveNewPhotos(FashionPostEntity post, String uploadPath,
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

    @Transactional
    public void deletePost(int postNum) {
        /* 설명. 1. 게시글 존재 여부 검사 */
        FashionPostEntity postToDelete = fashionPostRepository.findById(postNum)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        /* 설명. 2. 해시태그 삭제 */
        deleteHashtags(postNum);

        /* 설명. 3. 아이템 삭제 */
        deleteItems(postNum);

        /* 설명. 4. 사진 삭제 */
        List<PhotoEntity> photosToDelete = photoRepository.findAllByPostNumAndPhotoCategoryNum(postNum, 1);
        photosToDelete.addAll(photoRepository.findAllByPostNumAndPhotoCategoryNum(postNum, 4));
        for (PhotoEntity photo : photosToDelete) {
            File file = new File(photo.getPath() + File.separator + photo.getName());
            if(file.exists()) {
                boolean deleted = file.delete();
                if(!deleted) {
                    log.info("파일 삭제 중 이미지 파일 삭제에 실패했습니다");
                }
            }
        }
        photoRepository.deleteAll(photosToDelete);

        /* 설명. 5. 게시물 삭제 */
        fashionPostRepository.deleteById(postNum);
    }

    private void deleteHashtags(int postNum) {
        fashionHashRepository.deleteAllByFashionHashTagPK_PostNum(postNum);
    }

    private void deleteItems(int postNum) {
        fashionItemRepository.deleteAllByFashionPostItemPK_PostNum(postNum);
    }
}
