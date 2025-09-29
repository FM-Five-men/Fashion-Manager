package fashionmanager.lee.develop.service;

import fashionmanager.lee.develop.dto.CommentDTO;
import fashionmanager.lee.develop.dto.CommentPostDTO;
import fashionmanager.lee.develop.entity.Comment;
import fashionmanager.kim.develop.entity.Member;
import fashionmanager.lee.develop.repository.CommentRepository;
import fashionmanager.kim.develop.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional // 각 테스트 후 DB 변경사항을 롤백하여 테스트 독립성 보장
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository; // 테스트 데이터 생성을 위해 주입

    private Member testMember;

    @BeforeEach
    void setUp() {
        Member member = new Member();
        member.setMemberId("testuser");
        member.setMemberPwd("1234");
        member.setMemberEmail("test@test.com");
        member.setMemberName("테스트유저");
        member.setMemberAge(25);
        member.setMemberGender('남');
        member.setMemberStatus("활동중");
        testMember = memberRepository.save(member);
    }

    @Test
    @DisplayName("댓글 생성 후 조회 시, 생성된 댓글이 포함되어야 한다")
    void createAndFindComment_Success() {
        // Given: 새로운 댓글 정보 생성
        CommentPostDTO newCommentDto = new CommentPostDTO();
        newCommentDto.setContent("새로운 댓글");
        newCommentDto.setMemberNum(testMember.getMemberNum());
        newCommentDto.setPostNum(1); // 1번 fashion 게시물에 작성
        newCommentDto.setPostType("fashion");

        // When: 댓글 생성 서비스 호출
        CommentDTO createdComment = commentService.createComment(newCommentDto);

        // Then: 생성된 댓글 정보가 올바른지 확인
        assertThat(createdComment).isNotNull();
        assertThat(createdComment.getContent()).isEqualTo("새로운 댓글");

        // And: 해당 게시물의 댓글 목록을 조회했을 때 방금 생성한 댓글이 포함되어 있는지 확인
        List<CommentDTO> comments = commentService.findComments("fashion", 1);
        assertThat(comments).isNotEmpty();
        assertThat(comments).extracting(CommentDTO::getContent).contains("새로운 댓글");
    }

    @Test
    @DisplayName("기존 댓글 수정 시, 내용이 변경되어야 한다")
    void updateComment_Success() {
        // Given: 테스트용 댓글을 DB에 미리 저장
        Comment originalComment = new Comment("원본 내용", testMember.getMemberNum(), 1, "fashion");
        Comment savedComment = commentRepository.save(originalComment);
        Integer commentNum = savedComment.getNum();
        String updatedContent = "수정된 내용입니다.";

        // When: 댓글 수정 서비스 호출
        CommentDTO updatedComment = commentService.updateComment(commentNum, updatedContent);

        // Then: 반환된 DTO와 DB의 실제 데이터가 모두 변경되었는지 확인
        assertThat(updatedComment.getContent()).isEqualTo(updatedContent);

        Optional<Comment> foundComment = commentRepository.findById(commentNum);
        assertThat(foundComment).isPresent();
        assertThat(foundComment.get().getContent()).isEqualTo(updatedContent);
    }

    @Test
    @DisplayName("댓글 삭제 시, DB에서 실제로 삭제되어야 한다")
    void deleteComment_Success() {
        // Given: 테스트용 댓글을 DB에 미리 저장
        Comment commentToDelete = new Comment("삭제될 댓글", testMember.getMemberNum(), 1, "fashion");
        Comment savedComment = commentRepository.save(commentToDelete);
        Integer commentNum = savedComment.getNum();

        // When: 댓글 삭제 서비스 호출
        commentService.deleteComment(commentNum);

        // Then: DB에서 해당 댓글이 존재하지 않는지 확인
        boolean exists = commentRepository.existsById(commentNum);
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("댓글에 '좋아요' 반응 추가 시, 카운트가 1 증가해야 한다")
    void toggleReaction_AddNewGoodReaction_Success() {
        // Given: 테스트용 댓글을 DB에 미리 저장
        Comment comment = new Comment("좋아요 테스트 댓글", testMember.getMemberNum(), 1, "fashion");
        Comment savedComment = commentRepository.save(comment);
        Integer commentNum = savedComment.getNum();

        // When: 다른 사용자가 'GOOD' 반응을 추가
        CommentDTO reactedComment = commentService.toggleReaction(commentNum, testMember.getMemberNum(), "GOOD");

        // Then: 댓글의 good 카운트가 1로 변경되었는지 확인
        assertThat(reactedComment.getGood()).isEqualTo(1);
        assertThat(reactedComment.getCheer()).isEqualTo(0);
    }
}