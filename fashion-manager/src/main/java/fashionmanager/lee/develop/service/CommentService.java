package fashionmanager.lee.develop.service;

import fashionmanager.lee.develop.dto.CommentDTO;
import fashionmanager.lee.develop.dto.CommentPostDTO;
import fashionmanager.lee.develop.entity.Comment;
import fashionmanager.lee.develop.mapper.CommentMapper;
import fashionmanager.lee.develop.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    public List<CommentDTO> findComments(String postType, Integer postNum) {

        if (!List.of("fashion", "review", "mentoring").contains(postType)) {
            throw new IllegalArgumentException("Invalid post type provided");
        }
        return commentMapper.findCommentsByPost(postType, postNum);
    }

    @Transactional
    public CommentDTO createComment(CommentPostDTO commentPostDto) {
        Comment commentToSave = new Comment(
                commentPostDto.getContent(),
                commentPostDto.getMemberNum(),
                commentPostDto.getPostNum(),
                commentPostDto.getPostType()
        );

        Comment savedComment = commentRepository.save(commentToSave);

        return commentMapper.findCommentByNum(savedComment.getNum())
                .orElseThrow(() -> new RuntimeException("Failed to retrieve comment after creation"));
    }

    @Transactional
    public CommentDTO updateComment(Integer commentNum, String content) {
        Comment comment = commentRepository.findById(commentNum)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with num: " + commentNum));

        comment.setContent(content);

        return commentMapper.findCommentByNum(commentNum)
                .orElseThrow(() -> new RuntimeException("Failed to retrieve comment after update"));
    }

    @Transactional
    public void deleteComment(Integer commentNum) {
        if (!commentRepository.existsById(commentNum)) {
            throw new IllegalArgumentException("Comment not found with num: " + commentNum);
        }
        commentRepository.deleteById(commentNum);
    }
}