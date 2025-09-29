package fashionmanager.lee.develop.controller;

import fashionmanager.lee.develop.scheduler.MonthlyCountScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SchedulerController {

    private final MonthlyCountScheduler monthlyCountScheduler;

    @GetMapping("/scheduler/reset-counts")
    public String ResetMonthlyCounts() {
        // 스케줄러가 반환하는 결과 문자열을 그대로 클라이언트에게 전달
        return monthlyCountScheduler.resetMonthlyGoodCountManually();
    }
}