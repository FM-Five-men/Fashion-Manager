package fashionmanager.baek.develop.repository;

import fashionmanager.baek.develop.entity.ReviewPostItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReviewItemRepository extends JpaRepository<ReviewPostItemEntity, Integer> {

    List<ReviewPostItemEntity> findAllByReviewPostItemPK_PostNum(int postNum);

    void deleteAllByReviewPostItemPK_PostNumAndReviewPostItemPK_ItemNumIn(int postNum, List<Integer> itemNum);

    void deleteAllByReviewPostItemPK_PostNum(int postNum);
}
