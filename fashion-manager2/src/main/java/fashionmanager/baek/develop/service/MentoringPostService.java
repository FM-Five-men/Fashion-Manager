package fashionmanager.baek.develop.service;

import fashionmanager.baek.develop.dto.*;
import fashionmanager.baek.develop.entity.MentoringPostEntity;
import fashionmanager.baek.develop.entity.PhotoEntity;
import fashionmanager.baek.develop.mapper.MentoringPostMapper;
import fashionmanager.baek.develop.repository.MentoringPostRespository;
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

@Slf4j
@Service
public class MentoringPostService {
    private final MentoringPostRespository mentoringPostRespository;
    private final PhotoRepository photoRepository;
    private final MentoringPostMapper mentoringPostMapper;
    private String postUploadPath = "C:\\uploadFiles\\mentoring";

    @Autowired
    public MentoringPostService(MentoringPostRespository mentoringPostRespository,
                                PhotoRepository photoRepository, MentoringPostMapper mentoringPostMapper) {
        this.mentoringPostRespository = mentoringPostRespository;
        this.photoRepository = photoRepository;
        this.mentoringPostMapper = mentoringPostMapper;
    }

    public List<SelectAllMentoringPostDTO> getPostList() {
        return mentoringPostMapper.findAll();
    }

    public SelectDetailMentoringPostDTO getDetailPost(int postNum) {
        SelectDetailMentoringPostDTO postDetail = mentoringPostMapper.findById(postNum);
        if (postDetail == null) {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.");
        }
        return postDetail;
    }

    @Transactional
    public MentoringRegistResponseDTO registPost(MentoringRegistRequestDTO newPost, List<MultipartFile> postFiles,
                                        List<MultipartFile> itemFiles) {
        MentoringPostEntity mentoringPostEntity = changeToRegistPost(newPost);
        MentoringPostEntity registMentoringPost =  mentoringPostRespository.save(mentoringPostEntity);
        int postNum = registMentoringPost.getNum();

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
                photoEntity.setPhotoCategoryNum(3); // 멘토링 게시물 사진은 3
                photoRepository.save(photoEntity);
            }
        }

        MentoringRegistResponseDTO response = new MentoringRegistResponseDTO();
        response.setNum(postNum);
        response.setTitle(registMentoringPost.getTitle());
        response.setContent(registMentoringPost.getContent());
        response.setAuthorNum(registMentoringPost.getAuthorNum());
        response.setFinish(registMentoringPost.isFinish());
        return response;
    }

    private MentoringPostEntity changeToRegistPost(MentoringRegistRequestDTO newPost) {
        MentoringPostEntity mentoringPostEntity = new MentoringPostEntity();
        mentoringPostEntity.setTitle(newPost.getTitle());
        mentoringPostEntity.setContent(newPost.getContent());
        mentoringPostEntity.setFinish(newPost.isFinish());
        mentoringPostEntity.setAuthorNum(newPost.getAuthorNum());

        return mentoringPostEntity;
    }


    @Transactional
    public MentoringModifyResponseDTO modifyPost(int postNum, MentoringModifyRequestDTO updatePost,
                                                 List<MultipartFile> postFiles, List<MultipartFile> itemFiles) {
        MentoringPostEntity mentoringPostEntity = mentoringPostRespository.findById(postNum)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. id=" + postNum));

        mentoringPostEntity.setTitle(updatePost.getTitle());
        mentoringPostEntity.setContent(updatePost.getContent());
        mentoringPostEntity.setFinish(updatePost.isFinish());
        mentoringPostEntity.setAuthorNum(updatePost.getAuthorNum());

        updatePhotos(mentoringPostEntity,this.postUploadPath, postFiles, 3);

        MentoringModifyResponseDTO response = new MentoringModifyResponseDTO();
        response.setNum(postNum);
        response.setTitle(mentoringPostEntity.getTitle());
        response.setContent(mentoringPostEntity.getContent());
        response.setFinish(mentoringPostEntity.isFinish());
        response.setAuthorNum(mentoringPostEntity.getAuthorNum());
        return response;
    }

    private void updatePhotos(MentoringPostEntity post, String uploadPath, List<MultipartFile> newImageFiles, int categoryNum) {
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
    private void saveNewPhotos(MentoringPostEntity post, String uploadPath,
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
        MentoringPostEntity mentoringToDelete = mentoringPostRespository.findById(postNum)
                .orElseThrow(() ->new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        List<PhotoEntity> photosToDelete = photoRepository.findAllByPostNumAndPhotoCategoryNum(postNum, 3);
        for (PhotoEntity photo : photosToDelete) {
            File file = new File(photo.getPath() + File.separator + photo.getName());
            boolean deleted = file.delete();
            if(!deleted) {
                log.info("파일 삭제 중 이미지 파일 삭제에 실패했습니다");
            }
        }
        photoRepository.deleteAll(photosToDelete);

        mentoringPostRespository.deleteById(postNum);
    }
}
