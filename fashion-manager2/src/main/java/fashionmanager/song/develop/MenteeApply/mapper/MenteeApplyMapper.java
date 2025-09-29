package fashionmanager.song.develop.menteeApply.mapper;

import fashionmanager.song.develop.menteeApply.dto.MenteeApplyResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenteeApplyMapper {

    List<MenteeApplyResponseDTO> selectResultApply(@Param("content") String content,
                                                   @Param("accept") String accept,
                                                   @Param("mentoringPostNum") Integer mentoringPostNum,
                                                   @Param("memberName") String memberName);
}
