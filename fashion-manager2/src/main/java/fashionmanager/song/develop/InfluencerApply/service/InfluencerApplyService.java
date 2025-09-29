package fashionmanager.song.develop.influencerApply.service;

import fashionmanager.baek.develop.entity.PhotoEntity;
import fashionmanager.baek.develop.repository.PhotoRepository;
import fashionmanager.song.develop.common.PhotoType;
import fashionmanager.song.develop.influencerApply.aggregate.InfluencerApplyEntity;
import fashionmanager.song.develop.influencerApply.dto.InfluencerApplyCreateRequestDTO;
import fashionmanager.song.develop.influencerApply.dto.InfluencerApplyResponseDTO;
import fashionmanager.song.develop.influencerApply.mapper.InfluencerApplyMapper;
import fashionmanager.song.develop.influencerApply.repository.InfluencerApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor   // 생성자 자동으로 해주는 어노테이션
public class InfluencerApplyService {

    private final InfluencerApplyMapper influencerApplyMapper;
    private final InfluencerApplyRepository influencerApplyRepository;

    /*이미지 파일 업로드 코드 추가*/
    private final PhotoRepository photoRepository;
    private String UploadPath = "C:\\uploadFiles\\Influencer_Apply";
    // PhotoType에서 페이지 코드 가져옴
    private static final int INFLUENCER_APPLY_CODE = PhotoType.INFLUENCER_APPLY.getCode();


    // 인플루언서 신청 전체 조회
    public List<InfluencerApplyResponseDTO> selectResultApply(String title,
                                                              String content,
                                                              String accept,
                                                              Integer memberNum,
                                                              String memberName) {

        List<InfluencerApplyResponseDTO> list =
                influencerApplyMapper.selectResultApply(
                        title, content, accept, memberNum, memberName);

        // 각각 페이지의 사진 목록을 조회해서 /files/influencer_Apply/파일명 형태의 URL로 세팅
        for (InfluencerApplyResponseDTO dto : list) {
            int pageNum = dto.getNum();
            List<String> urls = photoRepository
                    .findAllByPostNumAndPhotoCategoryNum(pageNum, INFLUENCER_APPLY_CODE)
                    .stream()
                    // 정적 리소스 매핑과 맞춘 상대경로로 응답 (브라우저에서 바로 접근 가능)
                    .map(p -> "/files/influencer_apply/" + p.getName())
                    .toList();
            dto.setPhotoPaths(urls);
        }
        return list;
    }



    // 인플루언서 신청
    @Transactional
    public InfluencerApplyCreateRequestDTO insertInfluencerApply(InfluencerApplyCreateRequestDTO req,
                                                                 List<MultipartFile> postFiles) {
//         신청서 상태는 회원이 넣는 것이 아니기 때문에 null 혹은 blank 일떄는 default 상태 -> '대기'
        if (req.getAccept() == null || req.getAccept().isBlank()) {
            req.setAccept("대기");
        }

        InfluencerApplyEntity entity = new InfluencerApplyEntity();
        entity.setTitle(req.getTitle());
        entity.setContent(req.getContent());
        entity.setAccept(req.getAccept());
        entity.setMemberNum(req.getMemberNum());

        InfluencerApplyEntity entitySaved = influencerApplyRepository.save(entity);

        /*  사진 저장: 디스크 저장 + photo 테이블 insert (postNum과 카테고리 코드 필수) */
        if (postFiles != null && !postFiles.isEmpty()) {
            File uploadDir = new File(UploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            for (MultipartFile imageFile : postFiles) {
                // 원본 파일 이름 저장
                String originalFileName = imageFile.getOriginalFilename();

                // 원본 파일 이름이 없거나 혹은 파일 이름에 .이 포함 되어 있지 않으면 예외 처리
                if (originalFileName == null || !originalFileName.contains(".")) {
                    throw new RuntimeException("유효하지 않은 파일명입니다.");
                }

                // 확장자명 뽑기
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

                // 파일 이름 리네임
                String savedFileName = UUID.randomUUID().toString() + extension;


                File targetFile = new File(UploadPath + File.separator + savedFileName);
                try {
                    imageFile.transferTo(targetFile);
                } catch (IOException e) {
                    throw new RuntimeException("파일 저장에 실패했습니다", e);
                }

                PhotoEntity photoEntity = new PhotoEntity();
                photoEntity.setName(savedFileName);
                photoEntity.setPath(UploadPath);
                photoEntity.setPhotoCategoryNum(INFLUENCER_APPLY_CODE);
                photoEntity.setPostNum(entitySaved.getNum());
                photoRepository.save(photoEntity);
            }
        }

        InfluencerApplyCreateRequestDTO reqSaved = new InfluencerApplyCreateRequestDTO();
        reqSaved.setNum(entitySaved.getNum());
        reqSaved.setTitle(entitySaved.getTitle());
        reqSaved.setContent(entitySaved.getContent());
        reqSaved.setAccept(entitySaved.getAccept());
        reqSaved.setMemberNum(entitySaved.getMemberNum());
        return reqSaved;
    }




    // 인플루언서 신청 수정

    @Transactional
    public int updateInfluencerApply(InfluencerApplyResponseDTO req) {
        return influencerApplyRepository.findByNumAndMemberNum(req.getNum(), req.getMemberNum())
                .map(entity -> {
                    entity.setTitle(req.getTitle());
                    entity.setContent(req.getContent());
                    influencerApplyRepository.save(entity);
                    return 1;
                })
                .orElse(0);
    }

    // 인플루언서 신청 삭제
    public int deleteInfluencerApplyByTitleAndMemberNum(String title, int memberNum) {
        return influencerApplyRepository.deleteInfluencerApplyByTitleAndMemberNum(title, memberNum);
    }
}