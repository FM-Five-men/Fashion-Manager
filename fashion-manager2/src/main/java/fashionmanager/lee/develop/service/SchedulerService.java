package fashionmanager.lee.develop.service;

import fashionmanager.lee.develop.repository.SchedulerMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final SchedulerMemberRepository schedulerMemberRepository;

    @Transactional
    public void resetMonthlyGoodCounts() {
        schedulerMemberRepository.resetAllMonthlyGoodCounts();
    }

    public Long getTotalMonthlyGoodCounts() {
        Long total = schedulerMemberRepository.sumMonthlyGoodCounts();
        // 만약 회원 데이터가 하나도 없어서 합계가 NULL이면 0을 반환
        return total == null ? 0L : total;
    }
}