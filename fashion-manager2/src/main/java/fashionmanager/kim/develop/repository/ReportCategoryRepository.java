package fashionmanager.kim.develop.repository;

import fashionmanager.kim.develop.entity.ReportCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReportCategoryRepository extends JpaRepository<ReportCategory,Integer> {

    @Query(value = "SELECT COALESCE(MAX(r.reportCategoryNum), 0) FROM ReportCategory r")
    int findMaxNum();

    @Modifying
    @Query(value = "UPDATE ReportCategory r SET r.reportCategoryName = :name WHERE r.reportCategoryNum = :num")
    int updateReportCategory(@Param("num") int num, @Param("name") String name);

    @Modifying
    @Query(value = "DELETE FROM ReportCategory r WHERE r.reportCategoryNum = :num")
    int deleteReviewCategory(@Param("num") int num);
}
