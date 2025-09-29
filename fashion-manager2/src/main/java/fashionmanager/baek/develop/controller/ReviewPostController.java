package fashionmanager.baek.develop.controller;

import fashionmanager.baek.develop.dto.*;
import fashionmanager.baek.develop.service.ReviewPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/posts/review")
public class ReviewPostController {
    private final ReviewPostService reviewPostService;

    @Autowired
    public ReviewPostController(ReviewPostService reviewPostService) {
        this.reviewPostService = reviewPostService;
    }

    @GetMapping
    public ResponseEntity<List<SelectAllReviewPostDTO>> getPostList() {
        List<SelectAllReviewPostDTO> response = reviewPostService.getPostList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postNum}")
    public ResponseEntity<SelectDetailReviewPostDTO> getDetailPost(@PathVariable int postNum) {

        SelectDetailReviewPostDTO response = reviewPostService.getDetailPost(postNum);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReviewRegistResponseDTO> registPost
            (@RequestPart("newPost") ReviewRegistRequestDTO newPost,
             @RequestPart(value = "postImages", required = false) List<MultipartFile> postFiles,
             @RequestPart(value = "itemImages", required = false) List<MultipartFile> itemFiles) {
        // 사진은 Nullable하므로 required = false // postService 결정

        ReviewRegistResponseDTO response = reviewPostService.registPost(newPost, postFiles, itemFiles); // method 작동

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{postNum}")
    public ResponseEntity<ReviewModifyResponseDTO> modifyPost
            (@PathVariable int postNum,
             @RequestPart("modifyPost") ReviewModifyRequestDTO modifyRequestDTO,
             @RequestPart(value = "postImages", required = false) List<MultipartFile> postFiles,
             @RequestPart(value = "itemImages", required = false) List<MultipartFile> itemFiles) {

        ReviewModifyResponseDTO response = reviewPostService.modifyPost(postNum, modifyRequestDTO, postFiles, itemFiles);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{postNum}")
    public ResponseEntity<String> deletePost (@PathVariable int postNum) {

        reviewPostService.deletePost(postNum);

        return ResponseEntity.ok("게시글이 성공적으로 삭제됐습니다.");
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
