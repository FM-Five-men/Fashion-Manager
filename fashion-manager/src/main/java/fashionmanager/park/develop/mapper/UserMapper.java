package fashionmanager.park.develop.mapper;


import fashionmanager.park.develop.menu.DTO.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    UserDTO selectUserByNum(int userNum);

    List<UserDTO> selectAllUsers();

}