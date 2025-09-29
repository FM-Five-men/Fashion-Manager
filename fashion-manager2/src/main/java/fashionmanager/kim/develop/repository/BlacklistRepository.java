package fashionmanager.kim.develop.repository;

import fashionmanager.kim.develop.entity.Blacklist;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BlacklistRepository extends JpaRepository<Blacklist, Integer> {
    @Modifying
    @Query("DELETE FROM Blacklist r WHERE r.blacklistNum = :num")
    int deleteBlacklist(@Param("num")int num);
}
