package fashionmanager.song.develop;

import fashionmanager.song.develop.influencerPage.dto.InfluencerPageCreateRequestDTO;
import fashionmanager.song.develop.influencerPage.dto.InfluencerPageResponseDTO;
import fashionmanager.song.develop.influencerPage.service.InfluencerPageService;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
// 아래 두개는 테스트 롤백용
@Transactional
@Rollback
public class InfluencerPageServiceTest {

    @Autowired
    private InfluencerPageService influencerPageService;

    @DisplayName("인플루언서 페이지 조회 - 전체 조회 테스트")
    @Test
    public void testSelectInfluencerPage1() {
        Assertions.assertDoesNotThrow(() -> {
            List<InfluencerPageResponseDTO> list =
                    influencerPageService.selectResultPage(
                            null, null, null, null);
            list.forEach(System.out::println);
        });
    }

    @DisplayName("인플루언서 페이지 조회 - 조건 조회 테스트")
    @Test
    public void testSelectInfluencerPage2() {
        Assertions.assertDoesNotThrow(() -> {
                    List<InfluencerPageResponseDTO> list =
                            influencerPageService.selectResultPage(
                                    "김패션의", null, "010-1111", null);

                    list.forEach(System.out::println);

                    // 더미데이터 검증 예시
                    Assertions.assertFalse(list.isEmpty(), "데이터가 없습니다.");
                }
        );
    }

    @DisplayName("인플루언서 페이지 등록 테스트")
    @Test
    void testInsertInfluencerPage() {

        InfluencerPageCreateRequestDTO req = new InfluencerPageCreateRequestDTO();
        req.setTitle("인플루언서 페이지 등록 테스트");
        req.setContent("insta, phone 둘 다 없음");
        req.setInsta(null);
        req.setPhone(null);
        req.setMemberNum(2);

        InfluencerPageCreateRequestDTO res
                = Assertions.assertDoesNotThrow(
                () -> influencerPageService.insertInfluencerPage(req, null)
        );


        Assertions.assertNotNull(res.getNum());
        Assertions.assertNull(res.getInsta());
        Assertions.assertNull(res.getPhone());
    }


    @DisplayName("인플루언서 페이지 삭제 테스트")
    @Test
    public void testDeleteInfluencerPage() {
        int result = influencerPageService
                .deleteInfluencerPageTitleAndMemberNum("김패션의 스타일링 공간", 2);
        Assertions.assertEquals(1, result);
    }



}

