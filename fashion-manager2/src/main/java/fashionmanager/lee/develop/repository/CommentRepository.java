package fashionmanager.lee.develop.repository;

import fashionmanager.lee.develop.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}