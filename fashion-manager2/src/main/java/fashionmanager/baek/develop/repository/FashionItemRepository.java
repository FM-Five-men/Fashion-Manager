package fashionmanager.baek.develop.repository;

import fashionmanager.baek.develop.entity.FashionPostItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FashionItemRepository extends JpaRepository<FashionPostItemEntity, Integer> {
    List<FashionPostItemEntity> findAllByFashionPostItemPK_PostNum(int postNum);

    void deleteAllByFashionPostItemPK_PostNumAndFashionPostItemPK_ItemNumIn(int postNum, List<Integer> itemNum);

    void deleteAllByFashionPostItemPK_PostNum(int postNum);
}
