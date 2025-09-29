package fashionmanager.lee.develop.repository;

import fashionmanager.kim.develop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerMemberRepository extends JpaRepository<Member, Integer> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.memberMonthlyGoodCount = 0")
    void resetAllMonthlyGoodCounts();

    @Query("SELECT SUM(m.memberMonthlyGoodCount) FROM Member m")
    Long sumMonthlyGoodCounts();
}