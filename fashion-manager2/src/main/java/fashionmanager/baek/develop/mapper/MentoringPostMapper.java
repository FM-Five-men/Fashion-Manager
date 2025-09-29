package fashionmanager.baek.develop.mapper;

import fashionmanager.baek.develop.dto.SelectAllFashionPostDTO;
import fashionmanager.baek.develop.dto.SelectAllMentoringPostDTO;
import fashionmanager.baek.develop.dto.SelectDetailMentoringPostDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MentoringPostMapper {
    List<SelectAllMentoringPostDTO> findAll();

    SelectDetailMentoringPostDTO findById(int postNum);
}
