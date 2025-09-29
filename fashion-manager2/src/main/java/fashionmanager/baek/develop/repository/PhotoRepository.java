package fashionmanager.baek.develop.repository;

import fashionmanager.baek.develop.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Integer> {
    List<PhotoEntity> findAllByPostNumAndPhotoCategoryNum(int postNum, int photoCategoryNum);
}
