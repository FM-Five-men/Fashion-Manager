package fashionmanager.kim.develop.repository;

import fashionmanager.kim.develop.entity.AssignedRight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AssignedRightRepository extends JpaRepository<AssignedRight, Integer> {
    @Modifying
    @Query("UPDATE AssignedRight r SET r.assignedRightMemberStateNum = :memberStateNum WHERE r.assignedRightMemberNum = :memberNum")
    int updateRight(int memberNum, int memberStateNum);
}
