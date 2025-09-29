package fashionmanager.kim.develop.repository;

import fashionmanager.kim.develop.entity.PhotoCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PhotoCategoryRepository extends JpaRepository<PhotoCategory,Integer> {
    @Query(value = "SELECT COALESCE(MAX(r.photoCategoryNum), 0) FROM PhotoCategory r")
    int findMaxNum();

    @Modifying
    @Query(value = "UPDATE PhotoCategory r SET r.photoCategoryName = :name WHERE r.photoCategoryNum = :num")
    int updatePhotoCategory(@Param("num") int num, @Param("name")String name);

    @Modifying
    @Query(value = "DELETE FROM PhotoCategory r WHERE r.photoCategoryNum = :num")
    int deletePhotoCategory(@Param("num") int num);
}
