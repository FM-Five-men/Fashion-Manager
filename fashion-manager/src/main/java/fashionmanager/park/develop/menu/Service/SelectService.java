package fashionmanager.park.develop.menu.Service;


import fashionmanager.park.develop.mapper.UserMapper;
import fashionmanager.park.develop.menu.DTO.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class SelectService {

    private final UserMapper userMapper;

    @Autowired
    public SelectService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    // 1. 회원번호로 특정회원 조회
    public UserDTO findUserByNum(int userNum) {
        return userMapper.selectUserByNum(userNum);
    }


    // 2. 전체 회원 조회
    public List<UserDTO> findAllUsers() {
        return userMapper.selectAllUsers();
    }



}
