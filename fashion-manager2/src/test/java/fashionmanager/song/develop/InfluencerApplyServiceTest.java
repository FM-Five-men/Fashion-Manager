package fashionmanager.song.develop;

import fashionmanager.song.develop.influencerApply.dto.InfluencerApplyCreateRequestDTO;
import fashionmanager.song.develop.influencerApply.dto.InfluencerApplyResponseDTO;
import fashionmanager.song.develop.influencerApply.repository.InfluencerApplyRepository;
import fashionmanager.song.develop.influencerApply.service.InfluencerApplyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
// 아래 두개는 테스트 롤백용
@Transactional
@Rollback
public class InfluencerApplyServiceTest {

    @Autowired
    private InfluencerApplyService influencerApplyService;
    private InfluencerApplyRepository influencerApplyRepository;

    @DisplayName("인플루언서 신청 조회 - 전체 조회 테스트 ")
    @Test
    void testSelectInfluencerApply1() {
        Assertions.assertDoesNotThrow(
                () -> {
                    List<InfluencerApplyResponseDTO> influencerList
                            = influencerApplyService.selectResultApply(
                            null, null, null, null, null
                    );
                    influencerList.forEach(System.out::println);
                });
    }

    @DisplayName("인플루언서 신청 조회 - 조건 조회 테스트")
    @Test
    public void testSelectInfluencerApply2() {
        Assertions.assertDoesNotThrow(() -> {
                    List<InfluencerApplyResponseDTO> influencerList =
                            influencerApplyService.selectResultApply(
                                    null, null, null, 7, null);

                    influencerList.forEach(System.out::println);

                    // 더미데이터 검증 예시
                    Assertions.assertFalse(influencerList.isEmpty(), "데이터가 없습니다.");
                }
        );
    }

    @DisplayName("인플루언서 신청 등록 테스트")
    @Test
    public void testInsertInfluencerApply() {

        InfluencerApplyCreateRequestDTO req = new InfluencerApplyCreateRequestDTO();
        req.setTitle("인플루언서 신청 테스트");
        req.setContent("이것은 인플루언서 신청 테스트 입니다.");
        req.setAccept("대기");
        req.setMemberNum(2);

        InfluencerApplyCreateRequestDTO res
                = Assertions.assertDoesNotThrow(
                () ->  influencerApplyService.insertInfluencerApply(req, null)
        );

        // 생성 된 신청 맞는지 확인
        Assertions.assertNotNull(res.getNum());
        Assertions.assertEquals("인플루언서 신청 테스트", res.getTitle());
        Assertions.assertEquals("이것은 인플루언서 신청 테스트 입니다.", res.getContent());
        Assertions.assertEquals("대기", res.getAccept());
        Assertions.assertEquals(2, res.getMemberNum());
    }


    @DisplayName("인플루언서 신청 삭제 테스트")
    @Test
    public void testDeleteInfluencerApply() {
        int result = influencerApplyService
                .deleteInfluencerApplyByTitleAndMemberNum(
                        "인플루언서 신청합니다! (user04)", 7);
        Assertions.assertEquals(1, result);
    }
}
