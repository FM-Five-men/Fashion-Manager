package fashionmanager.baek.develop.repository;

import fashionmanager.baek.develop.entity.FashionPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FashionPostRepository extends JpaRepository<FashionPostEntity, Integer> {
}
