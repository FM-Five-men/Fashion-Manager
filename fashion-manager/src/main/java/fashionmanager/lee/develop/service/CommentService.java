package fashionmanager.lee.develop.service;

import fashionmanager.lee.develop.dto.CommentDTO;
import fashionmanager.lee.develop.dto.CommentPostDTO;
import fashionmanager.lee.develop.entity.Comment;
import fashionmanager.lee.develop.entity.CommentReaction;
import fashionmanager.lee.develop.mapper.CommentMapper;
import fashionmanager.lee.develop.repository.CommentReactionRepository;
import fashionmanager.lee.develop.repository.CommentRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentReactionRepository commentReactionRepository;
    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    public List<CommentDTO> findComments(String postType, Integer postNum) {
        if (!List.of("fashion", "review", "mentoring").contains(postType)) {
            throw new IllegalArgumentException("유효하지 않은 게시물 타입입니다.");
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
        Comment savedComment = commentRepository.saveAndFlush(commentToSave);
//        Comment savedComment = commentRepository.save(commentToSave);
        return commentMapper
                .findCommentByNum(savedComment.getNum())
                .orElseThrow(() -> new RuntimeException("댓글 생성 후 정보를 가져오는 데 실패했습니다."));
    }

    @Transactional
    public CommentDTO updateComment(Integer commentNum, String content) {
        Comment comment = commentRepository
                .findById(commentNum)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다. 번호: " + commentNum));
        comment.setContent(content);
        commentRepository.flush();
        return commentMapper
                .findCommentByNum(commentNum)
                .orElseThrow(() -> new RuntimeException("댓글 수정 후 정보를 가져오는 데 실패했습니다."));
    }

    @Transactional
    public void deleteComment(Integer commentNum) {
        if (!commentRepository.existsById(commentNum)) {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다. 번호: " + commentNum);
        }
        commentRepository.deleteById(commentNum);
    }

    @Transactional
    public CommentDTO toggleReaction(Integer commentNum, Integer memberNum, String newReactionType) {
        final String newReaction = newReactionType.toUpperCase();

        Comment comment = commentRepository
                .findById(commentNum)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다. 번호: " + commentNum));

        Optional<CommentReaction> existingReactionOpt = commentReactionRepository.findByMemberNumAndCommentNum(
                memberNum,
                commentNum
        );

        if (existingReactionOpt.isPresent()) {
            CommentReaction existingReactionEntity = existingReactionOpt.get();
            String oldReaction = existingReactionEntity.getReactionType();

            if (oldReaction.equals(newReaction)) {
                commentReactionRepository.delete(existingReactionEntity);
                if (oldReaction.equals("GOOD")) {
                    comment.setGood(comment.getGood() - 1);
                } else {
                    comment.setCheer(comment.getCheer() - 1);
                }
            } else {
                throw new IllegalStateException("기존 반응을 먼저 취소해야 합니다.");
            }
        } else {
            CommentReaction reactionToSave = new CommentReaction(memberNum, commentNum, newReaction);
            commentReactionRepository.save(reactionToSave);
            if (newReaction.equals("GOOD")) {
                comment.setGood(comment.getGood() + 1);
            } else {
                comment.setCheer(comment.getCheer() + 1);
            }
        }
        commentRepository.flush();
        
        return commentMapper
                .findCommentByNum(commentNum)
                .orElseThrow(() -> new RuntimeException("댓글 정보를 갱신하는 데 실패했습니다."));
    }
}