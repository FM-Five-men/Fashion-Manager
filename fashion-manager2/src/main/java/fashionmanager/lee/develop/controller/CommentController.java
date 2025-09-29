package fashionmanager.lee.develop.controller;

import fashionmanager.lee.develop.dto.CommentDTO;
import fashionmanager.lee.develop.dto.CommentPostDTO;
import fashionmanager.lee.develop.dto.CommentReactionDTO;
import fashionmanager.lee.develop.service.CommentService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private static final Set<String> VALID_REACTION_TYPES = Set.of("good", "cheer");

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/getcomments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@RequestParam String postType, @RequestParam Integer postNum) {
        List<CommentDTO> comments = commentService.findComments(postType, postNum);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/createcomment")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentPostDTO commentPostDto) {
        CommentDTO createdComment = commentService.createComment(commentPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PutMapping("/updatecomment")
    public ResponseEntity<CommentDTO> updateComment(@RequestParam Integer commentNum, @RequestBody Map<String, String> payload) {
        String content = payload.get("content");
        if (content == null || content.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        CommentDTO updatedComment = commentService.updateComment(commentNum, content);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/deletecomment")
    public ResponseEntity<Void> deleteComment(@RequestParam Integer commentNum) {
        commentService.deleteComment(commentNum);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{commentNum}/react")
    public ResponseEntity<?> toggleCommentReaction(
            @PathVariable Integer commentNum,
            @RequestBody CommentReactionDTO reactionDto
    ) {
        if (
                reactionDto.getReactionType() == null ||
                        !VALID_REACTION_TYPES.contains(reactionDto.getReactionType().toLowerCase())
        ) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid reactionType. Must be 'good' or 'cheer'."));
        }

        try {
            CommentDTO updatedComment = commentService.toggleReaction(
                    commentNum,
                    reactionDto.getMemberNum(),
                    reactionDto.getReactionType()
            );
            return ResponseEntity.ok(updatedComment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }
}