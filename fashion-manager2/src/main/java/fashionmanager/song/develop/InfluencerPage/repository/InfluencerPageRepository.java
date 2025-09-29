package fashionmanager.song.develop.influencerPage.repository;

import fashionmanager.song.develop.influencerPage.aggregate.InfluencerPageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface InfluencerPageRepository extends JpaRepository<InfluencerPageEntity, Integer> {

    // 이거 이름조심 Optional 써서 이름 이상하면 바로 오류남
    Optional<InfluencerPageEntity> findByNumAndMemberNumAndMember_MemberName(int num, int memberNum, String memberName);

    @Modifying
    @Transactional
    @Query(value = """
    DELETE 
    FROM Influencer_Page
    WHERE title = :title 
    AND member_num = :memberNum
        """, nativeQuery = true)
    int deleteInfluencerPageTitleAndMemberNum(@Param("title") String title,
                                              @Param("memberNum") Integer memberNum);
}
