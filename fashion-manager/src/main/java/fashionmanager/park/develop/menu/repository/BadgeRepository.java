package fashionmanager.park.develop.menu.repository;

import fashionmanager.park.develop.menu.Entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Integer> {}