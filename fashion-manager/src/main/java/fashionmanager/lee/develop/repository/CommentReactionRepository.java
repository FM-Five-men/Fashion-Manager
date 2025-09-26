package fashionmanager.lee.develop.repository;

import fashionmanager.lee.develop.entity.CommentReaction;
import fashionmanager.lee.develop.entity.CommentReactionId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, CommentReactionId> {
    Optional<CommentReaction> findByMemberNumAndCommentNum(Integer memberNum, Integer commentNum);
}