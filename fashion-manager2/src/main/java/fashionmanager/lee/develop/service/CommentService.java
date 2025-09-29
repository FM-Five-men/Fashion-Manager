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
        // newReactionType을 대문자로 통일
        final String newReaction = newReactionType.toUpperCase();

        // 댓글 존재 여부 확인
        if (!commentRepository.existsById(commentNum)) {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다. 번호: " + commentNum);
        }

        // 기존 반응 정보 조회
        Optional<CommentReaction> existingReactionOpt = commentReactionRepository.findByMemberNumAndCommentNum(
                memberNum,
                commentNum
        );

        if (existingReactionOpt.isPresent()) {
            // 기존 반응이 있는 경우
            CommentReaction existingReaction = existingReactionOpt.get();
            String oldReaction = existingReaction.getReactionType();

            if (oldReaction.equals(newReaction)) {
                // 1. 기존 반응과 같은 반응이면 -> 삭제 (토글 OFF)
                commentReactionRepository.delete(existingReaction);
            } else {
                // 2. 기존 반응과 다른 반응이면 -> 반응 종류 변경 (좋아요 -> 힘내요)
                // 현재 트리거가 INSERT/DELETE에만 반응하므로, 삭제 후 새로 삽입하여 트리거를 활성화
                commentReactionRepository.delete(existingReaction);
                CommentReaction reactionToSave = new CommentReaction(memberNum, commentNum, newReaction);
                commentReactionRepository.save(reactionToSave);
            }
        } else {
            // 3. 기존 반응이 없는 경우 -> 새로 추가
            CommentReaction reactionToSave = new CommentReaction(memberNum, commentNum, newReaction);
            commentReactionRepository.save(reactionToSave);
        }

        // 데이터베이스 변경사항 즉시 반영
        commentReactionRepository.flush();

        // 변경된 최신 댓글 정보를 MyBatis를 통해 조회하여 반환
        return commentMapper
                .findCommentByNum(commentNum)
                .orElseThrow(() -> new RuntimeException("댓글 정보를 갱신하는 데 실패했습니다."));
    }
}