package fashionmanager.park.develop.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fashionmanager.park.develop.menu.Entity.User;



public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUserId(String userId);
    boolean existsByUserEmail(String userEmail);

}