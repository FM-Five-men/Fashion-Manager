package fashionmanager.song.develop;

import fashionmanager.song.develop.influencerPage.dto.InfluencerPageResponseDTO;
import fashionmanager.song.develop.menteeApply.dto.MenteeApplyCreateRequestDTO;
import fashionmanager.song.develop.menteeApply.dto.MenteeApplyResponseDTO;
import fashionmanager.song.develop.menteeApply.service.MenteeApplyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback
public class MenteeApplyServiceTest {

    @Autowired
    private MenteeApplyService menteeApplyService;

    @DisplayName("멘토링 신청 조회 - 전체 조회 테스트")
    @Test
    public void testSelectMenteeApply1() {
        Assertions.assertDoesNotThrow(() -> {
            List<MenteeApplyResponseDTO> list =
                    menteeApplyService.selectResultApply(
                            null, null, null, null);
            list.forEach(System.out::println);
        });
    }

    @DisplayName("멘토링 신청 조회 - 조건 조회 테스트")
    @Test
    public void testSelectMenteeApply2() {
        Assertions.assertDoesNotThrow(() -> {
                    List<MenteeApplyResponseDTO> list =
                            menteeApplyService.selectResultApply(
                                    null, null, 1, null);

                    list.forEach(System.out::println);

                    // 더미데이터 검증 예시
                    Assertions.assertFalse(list.isEmpty(), "데이터가 없습니다.");
                }
        );
    }

    @DisplayName("멘토링 신청 등록 테스트")
    @Test
    public void testInsertInfluencerApply() {

        MenteeApplyCreateRequestDTO req = new MenteeApplyCreateRequestDTO();
        req.setContent("멘토링 신청 등록 테스트");
        req.setAccept("대기");
        req.setMentoringPostNum(1);
        req.setMemberNum(2);

        MenteeApplyCreateRequestDTO res
                = Assertions.assertDoesNotThrow(
                () -> menteeApplyService.insertMenteeApply(req,null)
        );

        Assertions.assertEquals("대기", res.getAccept());
        Assertions.assertEquals(1, res.getMentoringPostNum());
        Assertions.assertEquals(2, res.getMemberNum());
    }

    @DisplayName("멘토링 신청 삭제 테스트")
    @Test
    public void testDeleteMenteeApply() {
        int result = menteeApplyService
                .deleteMenteeApplyByMentoringPostNumAndMemberNum(
                        "퍼스널 컬러 진단 받아보고 싶어서 신청합니다!", 1 ,8);
        Assertions.assertEquals(1, result);
    }
}
