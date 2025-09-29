package fashionmanager.song.develop.influencerApply.mapper;

import fashionmanager.song.develop.influencerApply.dto.InfluencerApplyResponseDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InfluencerApplyMapper {

    List<InfluencerApplyResponseDTO> selectResultApply(@Param("title") String title,
                                                       @Param("content") String content,
                                                       @Param("accept") String accept,
                                                       @Param("memberNum") Integer memberNum,
                                                       @Param("memberName") String memberName);
}