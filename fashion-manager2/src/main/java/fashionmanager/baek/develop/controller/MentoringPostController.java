package fashionmanager.baek.develop.controller;

import fashionmanager.baek.develop.dto.*;
import fashionmanager.baek.develop.service.MentoringPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/posts/mentoring")
public class MentoringPostController {
    private final MentoringPostService mentoringPostService;

    @Autowired
    public MentoringPostController(MentoringPostService mentoringPostService) {
        this.mentoringPostService = mentoringPostService;
    }

    @GetMapping
    public ResponseEntity<List<SelectAllMentoringPostDTO>> getPostList() {
        List<SelectAllMentoringPostDTO> response = mentoringPostService.getPostList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postNum}")
    public ResponseEntity<SelectDetailMentoringPostDTO> getDetailPost(@PathVariable int postNum) {

        SelectDetailMentoringPostDTO response = mentoringPostService.getDetailPost(postNum);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MentoringRegistResponseDTO> registPost
            (@RequestPart("newPost") MentoringRegistRequestDTO newPost,
             @RequestPart(value = "postImages", required = false) List<MultipartFile> postFiles,
             @RequestPart(value = "itemImages", required = false) List<MultipartFile> itemFiles) {
        // 사진은 Nullable하므로 required = false // postService 결정

        MentoringRegistResponseDTO response = mentoringPostService.registPost(newPost, postFiles, itemFiles); // method 작동

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{postNum}")
    public ResponseEntity<MentoringModifyResponseDTO> modifyPost
            (@PathVariable int postNum,
             @RequestPart("modifyPost") MentoringModifyRequestDTO modifyRequestDTO,
             @RequestPart(value = "postImages", required = false) List<MultipartFile> postFiles,
             @RequestPart(value = "itemImages", required = false) List<MultipartFile> itemFiles) {

        MentoringModifyResponseDTO response = mentoringPostService.modifyPost(postNum, modifyRequestDTO, postFiles, itemFiles);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{postNum}")
    public ResponseEntity<String> deletePost (@PathVariable int postNum) {

        mentoringPostService.deletePost(postNum);

        return ResponseEntity.ok("게시글이 성공적으로 삭제됐습니다.");
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
