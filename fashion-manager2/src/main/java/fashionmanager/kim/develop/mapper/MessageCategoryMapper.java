package fashionmanager.kim.develop.mapper;

import fashionmanager.kim.develop.dto.MessageCategoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageCategoryMapper {
    List<MessageCategoryDTO> selectAllMessageCategories();
}
