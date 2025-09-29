package fashionmanager.song.develop.menteeApply.controller;

import fashionmanager.song.develop.menteeApply.dto.MenteeApplyCreateRequestDTO;
import fashionmanager.song.develop.menteeApply.dto.MenteeApplyResponseDTO;
import fashionmanager.song.develop.menteeApply.service.MenteeApplyService;
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
@RequestMapping("/menteeApply")
@Slf4j
public class MenteeApplyController {

    private final MenteeApplyService menteeApplyService;

    @Autowired
    public MenteeApplyController(MenteeApplyService menteeApplyService) {
        this.menteeApplyService = menteeApplyService;
    }


    // 멘토링 신청 조회
    @GetMapping("/selectMenteeApply")
    public ResponseEntity<List<MenteeApplyResponseDTO>> selectResultApply(
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String accept,
            @RequestParam(required = false) Integer mentoringPostNum,
            @RequestParam(required = false) String memberName) {
        List<MenteeApplyResponseDTO> MenteeApplyList
                = menteeApplyService.selectResultApply(
                        content,accept,mentoringPostNum,memberName);
        for (MenteeApplyResponseDTO MenteeApplyDTO : MenteeApplyList) {
            log.info("멘토링 신청 조회: {}", MenteeApplyDTO);
        }
        return ResponseEntity.ok(MenteeApplyList);
    }

    // 멘토링 신청
    // consumes = 이거 써서 반드시 multipart/form-data 로 요청해야 함 (JSON+파일 동시 전송), 이미지 멀티로 보낼때
    @PostMapping(value = "/insertMenteeApply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MenteeApplyCreateRequestDTO> insertMenteeApply(
            @ModelAttribute MenteeApplyCreateRequestDTO req,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        MenteeApplyCreateRequestDTO saved = menteeApplyService.insertMenteeApply(req, files);
        if (saved != null) {
            log.info("멘토링 신청 완료!: {}", saved);
            return ResponseEntity.ok(saved);
        } else {
            log.info("멘토링 신청 실패!: {}", req);
            return ResponseEntity.badRequest().build();
        }
    }

    // 멘토링 신청 수정
    @PutMapping("/updateMenteeApply")
    public ResponseEntity<Map<String,Object>> updateMenteeApply(
                                               @RequestBody MenteeApplyResponseDTO req) {
        int result = menteeApplyService.updateMenteeApply(req);

        Map<String, Object> body = new HashMap<>();
        body.put("멘토링 신청서 수정", result);

        if (result == 1) {
            log.info("멘토링 신청서 수정 완료!: {}", result);
            return ResponseEntity.ok(body);
        } else {
            log.warn("멘토링 신청서 수정 실패!: {}", req);
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/deleteMenteeApply")
    public ResponseEntity<Map<String, Object>> deleteMenteeApplyByMentoringPostNumAndMemberNum(
            @RequestParam String content,
            @RequestParam int mentoringPostNum,
            @RequestParam int memberNum) {

        int delete = menteeApplyService
                .deleteMenteeApplyByMentoringPostNumAndMemberNum(content, mentoringPostNum, memberNum);

        Map<String, Object> body = new HashMap<>();
        body.put("멘토링 신청서 삭제", delete);

        if (delete > 0) {
            log.info("멘토링 신청서 삭제 완료!: {}", delete);
            return ResponseEntity.ok(body);
        } else {
            log.info("멘토링 신청서 삭제 실패!: {}", delete);
            return ResponseEntity.badRequest().body(body);
        }
    }

}
