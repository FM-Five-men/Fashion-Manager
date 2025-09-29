package fashionmanager.song.develop.influencerPage.controller;

import fashionmanager.song.develop.influencerPage.dto.InfluencerPageCreateRequestDTO;
import fashionmanager.song.develop.influencerPage.dto.InfluencerPageResponseDTO;
import fashionmanager.song.develop.influencerPage.service.InfluencerPageReadService;
import fashionmanager.song.develop.influencerPage.service.InfluencerPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/influencerPage")
@Slf4j
public class InfluencerPageController {
    private final InfluencerPageService influencerPageService;



    @Autowired
    public InfluencerPageController(InfluencerPageService influencerPageService,
                                    InfluencerPageReadService influencerPageReadService) {
        this.influencerPageService = influencerPageService;
        this.influencerPageReadService = influencerPageReadService;
    }


    // 인플루언서 페이지 조건 조회
    @GetMapping("/selectInfluencerPage")
    public ResponseEntity<List<InfluencerPageResponseDTO>> searchResultPage(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String insta,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Integer memberNum) {
        List<InfluencerPageResponseDTO> searchPageList
                = influencerPageService.selectResultPage(
                        title, insta, phone, memberNum);
        for (InfluencerPageResponseDTO influencerPage : searchPageList) {
            log.info("인플루언서 페이지 조건 조회: {}", influencerPage);
        }
        return ResponseEntity.ok(searchPageList);
    }


    // 페이지네이션
    private final InfluencerPageReadService influencerPageReadService;

    @GetMapping("/selectInfluencerPage/{page}")
    public ResponseEntity<List<InfluencerPageResponseDTO>> selectInfluencerPage(
            @PathVariable int page,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String insta,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Integer memberNum
    ) {
        return ResponseEntity.ok(
                influencerPageReadService.getList(page, title, insta, phone, memberNum)
        );
    }



    //  인플루언서 페이지 생성 + 사진 추가
    //  consumes = 이거 써서 반드시 multipart/form-data 로 요청해야 함 (JSON+파일 동시 전송), 이미지 멀티로 보낼때
    @PostMapping(value = "/insertInfluencerPage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<InfluencerPageCreateRequestDTO> insertInfluencerPage(
                                @ModelAttribute InfluencerPageCreateRequestDTO req,        // 이러면 포스트맨에서 FORM-DATA
//                              @RequestPart("data") InfluencerPageCreateRequestDTO req,   // 이건 다른 대안
                                @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        InfluencerPageCreateRequestDTO saved = influencerPageService.insertInfluencerPage(req, files);
        if (saved != null) {
            log.info("인플루언서 페이지 생성 완료!: {}", saved);
            return ResponseEntity.ok(saved);
        } else {
            log.info("인플루언서 페이지 생성 실패!: {}", req);
            return ResponseEntity.badRequest().build();
        }
    }

    // 인플루언서 페이지 수정
    @PutMapping("/updateInfluencerPage")
    public ResponseEntity<Map<String, Object>> updateInfluencerPage(
                                                @RequestBody InfluencerPageResponseDTO req) {
        int result = influencerPageService.updateInfluencerPage(req);

        Map<String, Object> body = new HashMap<>();
        body.put("인플루언서 페이지 수정", result);

        if (result == 1) {
            log.info("인플루언서 페이지 생성 완료!: {}", result);
            return ResponseEntity.ok(body);
        } else {
            log.info("인플루언서 페이지 수정 실패: {}", req);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deleteInfluencerPage")
    public ResponseEntity<Map<String, Object>> deleteInfluencerPageTitleAndMemberNum(
            @RequestParam String title,
            @RequestParam int memberNum) {

        int delete = influencerPageService.deleteInfluencerPageTitleAndMemberNum(title, memberNum);

        Map<String, Object> body = new HashMap<>();
        body.put("인플루언서 페이지 삭제", delete);

        if (delete > 0) {
            log.info("인플루언서 페이지 삭제 완료!: {}", delete);
            return ResponseEntity.ok(body);
        } else {
            log.info("인플루언서 페이지 삭제 실패!: {}", delete);
            return ResponseEntity.badRequest().body(body);
        }
    }

}
