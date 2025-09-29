package fashionmanager.park.develop.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fashionmanager.park.develop.menu.Entity.Badge;


public interface BadgeRepository extends JpaRepository<Badge, Integer> {}