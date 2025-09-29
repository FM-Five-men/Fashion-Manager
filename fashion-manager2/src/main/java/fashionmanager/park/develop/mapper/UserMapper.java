package fashionmanager.park.develop.mapper;


import org.apache.ibatis.annotations.Mapper;
import fashionmanager.park.develop.menu.DTO.UserDTO;

import java.util.List;

@Mapper
public interface UserMapper {
    UserDTO selectUserByNum(int userNum);

    List<UserDTO> selectAllUsers();

}