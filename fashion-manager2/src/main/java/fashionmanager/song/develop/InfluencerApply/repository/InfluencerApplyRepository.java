package fashionmanager.song.develop.influencerApply.repository;

import fashionmanager.song.develop.influencerApply.aggregate.InfluencerApplyEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface InfluencerApplyRepository extends JpaRepository<InfluencerApplyEntity, Integer> {

    Optional<InfluencerApplyEntity> findByNumAndMemberNum(Integer num, Integer memberNum);


    @Modifying
    @Transactional
    @Query(value = """
    DELETE 
    FROM Influencer_Apply
    WHERE title = :title 
    AND member_num = :memberNum
        """, nativeQuery = true)
    int deleteInfluencerApplyByTitleAndMemberNum(@Param("title") String title,
                                                 @Param("memberNum") Integer memberNum);
}