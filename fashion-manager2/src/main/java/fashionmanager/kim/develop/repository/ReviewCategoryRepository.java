package fashionmanager.kim.develop.repository;

import fashionmanager.kim.develop.entity.ReviewCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReviewCategoryRepository extends JpaRepository<ReviewCategory,Integer> {

    @Query(value = "SELECT COALESCE(MAX(r.reviewCategoryNum), 0) FROM ReviewCategory r")
    int findMaxNum();

    @Modifying
    @Query(value = "UPDATE ReviewCategory r SET r.reviewCategoryName = :name WHERE r.reviewCategoryNum = :num")
    int updateReviewCategory(@Param("num") int num, @Param("name") String name);

    @Modifying
    @Query(value = "DELETE FROM ReviewCategory r WHERE r.reviewCategoryNum = :num")
    int deleteReviewCategory(@Param("num") int num);
}
