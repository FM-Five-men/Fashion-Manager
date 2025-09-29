package fashionmanager.song.develop.menteeApply.repository;

import fashionmanager.song.develop.menteeApply.aggregate.MenteeApplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface MenteeRepository extends JpaRepository<MenteeApplyEntity, Integer> {

    Optional<MenteeApplyEntity> findById(Integer id);

    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM mentee_apply
         WHERE ((:content IS NULL AND content IS NULL) OR content = :content)
           AND mentoring_post_num = :mentoringPostNum
           AND member_num = :memberNum
    """, nativeQuery = true)
    int deleteMenteeApplyByMentoringPostNumAndMemberNum(@Param("content") String content,
                                    @Param("mentoringPostNum") Integer mentoringPostNum,
                                    @Param("memberNum") Integer memberNum);
}

