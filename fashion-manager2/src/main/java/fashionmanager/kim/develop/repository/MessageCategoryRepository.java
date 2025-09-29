package fashionmanager.kim.develop.repository;

import fashionmanager.kim.develop.entity.MessageCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MessageCategoryRepository extends CrudRepository<MessageCategory,Integer> {
    @Query(value = "SELECT COALESCE(MAX(r.messageCategoryNum), 0) FROM MessageCategory r")
    int findMaxNum();

    @Modifying
    @Query(value = "UPDATE MessageCategory r SET r.messageCategoryName = :name WHERE r.messageCategoryNum = :num")
    int updateMessageCategory(@Param("num") int num, @Param("name") String name);

    @Modifying
    @Query(value = "DELETE FROM MessageCategory r WHERE r.messageCategoryNum = :num")
    int deleteMessageCategory(@Param("num") int num);
}
