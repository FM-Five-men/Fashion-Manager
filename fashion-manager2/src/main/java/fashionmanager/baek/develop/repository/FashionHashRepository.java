package fashionmanager.baek.develop.repository;

import fashionmanager.baek.develop.entity.FashionHashTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface FashionHashRepository extends JpaRepository<FashionHashTagEntity, Integer> {
    List<FashionHashTagEntity> findAllByFashionHashTagPK_PostNum(int postNum);

    void deleteAllByFashionHashTagPK_PostNumAndFashionHashTagPK_TagNumIn(int postNum, List<Integer> tagNums);

    void deleteAllByFashionHashTagPK_PostNum(int postNum);
}
