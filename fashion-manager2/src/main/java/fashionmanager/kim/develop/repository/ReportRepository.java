package fashionmanager.kim.develop.repository;

import fashionmanager.kim.develop.dto.InsertReportDTO;
import fashionmanager.kim.develop.entity.Report;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Modifying
    @Query("DELETE FROM Report r WHERE r.reportNum = :num")
    int deleteReport(@Param("num") int num);

    @Modifying
    @Query("UPDATE Report r SET r.reportState = :state WHERE r.reportNum = :num")
    int updateReportState(@Param("num") int num,@Param("state") String state);
}
