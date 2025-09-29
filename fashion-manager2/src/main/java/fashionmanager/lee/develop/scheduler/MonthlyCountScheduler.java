package fashionmanager.lee.develop.scheduler;

import fashionmanager.lee.develop.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MonthlyCountScheduler {

    private final SchedulerService schedulerService;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void scheduleMonthlyReset() {
        log.info("매월 누적 좋아요 카운트 초기화 작업을 시작합니다.");
        try {
            schedulerService.resetMonthlyGoodCounts();
            log.info("모든 회원의 누적 좋아요 카운트를 0으로 성공적으로 초기화했습니다.");
        } catch (Exception e) {
            log.error("누적 좋아요 카운트 초기화 작업 중 오류가 발생했습니다.", e);
        }
    }

    // 수동 실행 메소드가 이제 결과 문자열을 반환하도록 변경
    public String resetMonthlyGoodCountManually() {
        // 1. 초기화 전 총합
        long beforeTotal = schedulerService.getTotalMonthlyGoodCounts();

        // 2. 초기화 로직을 실행
        schedulerService.resetMonthlyGoodCounts();

        // 3. 초기화 후 총합
        long afterTotal = schedulerService.getTotalMonthlyGoodCounts();

        return String.format(
                "초기화 작업 완료. [초기화 전 총 좋아요: %d] -> [초기화 후 총 좋아요: %d]",
                beforeTotal,
                afterTotal
        );
    }
}