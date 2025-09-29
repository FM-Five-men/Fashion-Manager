package fashionmanager.kim.develop.repository;

import fashionmanager.kim.develop.entity.HashTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface HashTagRepository extends JpaRepository<HashTag, Integer> {
    @Query(value = "SELECT COALESCE(MAX(r.hashTagNum), 0) FROM HashTag r")
    int findMaxNum();

    @Modifying
    @Query(value = "UPDATE HashTag r SET r.hashTagName = :name WHERE r.hashTagNum = :num")
    int updateHashTag(@Param("num")int num, @Param("name")String name);

    @Modifying
    @Query(value = "DELETE FROM HashTag r WHERE r.hashTagNum = :num")
    int deleteHashTag(@Param("num")int num);
}
