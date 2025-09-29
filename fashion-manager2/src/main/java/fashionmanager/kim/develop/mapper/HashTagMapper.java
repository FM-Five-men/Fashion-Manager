package fashionmanager.kim.develop.mapper;

import fashionmanager.kim.develop.dto.HashTagAndPostDTO;
import fashionmanager.kim.develop.dto.HashTagDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HashTagMapper {
    List<HashTagDTO> selectHashTags();

    List<HashTagAndPostDTO> selectHashTagsAndPosts();
}
