package fashionmanager.lee.develop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@SpringBootTest
@Transactional
public class SchedulerServiceTest {

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    @DisplayName("테스트 데이터 준비")
    void setup() {
        // 모든 회원의 월간 좋아요 수를 50으로 설정.
        jdbcTemplate.update("UPDATE member SET monthly_good_count = 50");
    }

    @Test
    @Commit
    @DisplayName("매월 좋아요 횟수 초기화 기능 테스트")
    void testResetMonthlyGoodCounts() {
        // 초기화 전 좋아요 수 총합 조회 및 출력
        Map<String, Object> beforeResult = jdbcTemplate.queryForMap("SELECT SUM(monthly_good_count) as total FROM member");
        long beforeTotal = ((Number) beforeResult.get("total")).longValue();
        System.out.println("초기화 전 총 좋아요 수: " + beforeTotal);

        // 서비스 메소드 호출
        schedulerService.resetMonthlyGoodCounts();

        // 초기화 후 좋아요 수 총합 조회 및 출력
        Map<String, Object> afterResult = jdbcTemplate.queryForMap("SELECT SUM(monthly_good_count) as total FROM member");
        long afterTotal = ((Number) afterResult.get("total")).longValue();
        System.out.println("초기화 후 총 좋아요 수: " + afterTotal);

        Assertions.assertEquals(0, afterTotal, "월간 좋아요 수가 정상적으로 초기화되지 않았습니다.");
    }
}