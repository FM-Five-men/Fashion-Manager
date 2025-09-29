package fashionmanager.kim.develop.mapper;

import fashionmanager.kim.develop.dto.MemberDTO;
import fashionmanager.kim.develop.dto.MemberRightDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    MemberDTO selectMessageAllow(@Param("selectMemberId")String memberId);

    MemberDTO selectMemberByNum(@Param("selectMemberNum")int memberNum);

    List<MemberDTO> selectMember();

    MemberDTO selectMemberById(@Param("selectMemberId")String memberId);

    MemberRightDTO selectMemberRightById(@Param("selectMemberId")String memberId);
}
