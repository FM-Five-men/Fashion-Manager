package fashionmanager.lee.develop.service;

import static org.junit.jupiter.api.Assertions.*;

import fashionmanager.lee.develop.dto.CommentDTO;
import fashionmanager.lee.develop.dto.CommentPostDTO;
import fashionmanager.lee.develop.repository.CommentRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("CommentService 통합 테스트")
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository; // 삭제 확인용

    @Nested
    @DisplayName("댓글 CRUD 테스트")
    class CommentCrudTests {

        @Test
        @DisplayName("새로운 댓글을 성공적으로 생성한다")
        void createCommentTest() {
            // Given: 생성할 댓글 정보
            CommentPostDTO newCommentInfo = new CommentPostDTO("새 댓글 테스트입니다.", 4, 1, "fashion");

            // When: 댓글 생성 서비스 호출
            CommentDTO createdComment = commentService.createComment(newCommentInfo);

            // Then: 반환된 DTO와 DB에서 확인
            assertNotNull(createdComment);
            assertEquals("새 댓글 테스트입니다.", createdComment.getContent());
            assertEquals(4, createdComment.getMemberNum());
            assertTrue(commentRepository.existsById(createdComment.getNum()));
        }

        @Test
        @DisplayName("특정 게시물의 댓글 목록을 조회한다")
        void findCommentsByPostTest() {
            // Given: 패션 게시물 1번
            int postNum = 1;
            String postType = "fashion";

            // When: 댓글 목록 조회 서비스 호출
            List<CommentDTO> comments = commentService.findComments(postType, postNum);

            // Then: 더미 데이터에 따라 2개의 댓글이 조회되어야 함
            assertEquals(2, comments.size());
            assertEquals("와, 코디 너무 예뻐요! 정보 좀 알 수 있을까요?", comments.get(0).getContent());
        }

        @Test
        @DisplayName("기존 댓글의 내용을 수정한다")
        void updateCommentTest() {
            // Given: 1번 댓글과 새로운 내용
            int commentNum = 1;
            String newContent = "내용이 수정되었습니다.";

            // When: 댓글 수정 서비스 호출
            CommentDTO updatedComment = commentService.updateComment(commentNum, newContent);

            // Then: 반환된 DTO의 내용이 변경되었는지 확인
            assertEquals(newContent, updatedComment.getContent());
        }

        @Test
        @DisplayName("기존 댓글을 삭제한다")
        void deleteCommentTest() {
            // Given: 삭제할 댓글 번호 (1번)
            int commentNum = 1;
            assertTrue(commentRepository.existsById(commentNum)); // 삭제 전 존재 확인

            // When: 댓글 삭제 서비스 호출
            assertDoesNotThrow(() -> commentService.deleteComment(commentNum));

            // Then: DB에서 해당 댓글이 사라졌는지 확인
            assertFalse(commentRepository.existsById(commentNum));
        }

        @Test
        @DisplayName("존재하지 않는 댓글 수정/삭제 시 예외가 발생한다")
        void exceptionForNonExistentCommentTest() {
            // Given: 존재하지 않는 댓글 번호
            int invalidCommentNum = 9999;

            // When & Then
            assertThrows(IllegalArgumentException.class, () -> commentService.updateComment(invalidCommentNum, "내용"));
            assertThrows(IllegalArgumentException.class, () -> commentService.deleteComment(invalidCommentNum));
        }
    }

    @Nested
    @DisplayName("댓글 반응 테스트")
    class CommentReactionTests {

        @Test
        @DisplayName("하나의 댓글에 대한 반응 시나리오를 검증한다")
        void toggleReactionScenarioTest() {
            // Given: 1번 댓글과, 4번 회원 (더미 데이터상 반응 기록 없음)
            int commentNum = 1;
            int memberNum = 4;

            // 1. '좋아요'를 처음 누른다.
            CommentDTO resultLike = commentService.toggleReaction(commentNum, memberNum, "good");
            assertEquals(6, resultLike.getGood(), "최초 좋아요 시 good 카운트가 1 증가해야 합니다.");

            // 2. '좋아요'가 눌린 상태에서 '힘내요'를 누르면 예외가 발생한다.
            assertThrows(
                    IllegalStateException.class,
                    () -> commentService.toggleReaction(commentNum, memberNum, "cheer"),
                    "다른 반응 선택 시 IllegalStateException이 발생해야 합니다."
            );

            // 3. '좋아요'를 다시 눌러 취소한다.
            CommentDTO resultUnlike = commentService.toggleReaction(commentNum, memberNum, "good");
            assertEquals(5, resultUnlike.getGood(), "좋아요 취소 시 good 카운트가 1 감소해야 합니다.");

            // 4. 반응이 없는 상태에서 '힘내요'를 누른다.
            CommentDTO resultCheer = commentService.toggleReaction(commentNum, memberNum, "cheer");
            assertEquals(6, resultCheer.getGood(), "좋아요 수는 그대로여야 합니다.");
            assertEquals(3, resultCheer.getCheer(), "힘내요 선택 시 cheer 카운트가 1 증가해야 합니다.");
        }
    }
}