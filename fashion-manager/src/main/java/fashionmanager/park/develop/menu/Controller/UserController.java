package fashionmanager.park.develop.menu.Controller;

import fashionmanager.park.develop.menu.DTO.UserDTO;
import fashionmanager.park.develop.menu.Service.SelectService;
import fashionmanager.park.develop.menu.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
@Slf4j
public class UserController {

    private UserService userService;
    private final SelectService selectService;

    public UserController(UserService userService, SelectService selectService)
    {
        this.userService = userService;
        this.selectService = selectService;
    }




//    // 1. 회원 조회 페이지(JPA)
//
//    @GetMapping("/select")
//    public void selectMenu() {
//
//    }




    // 2. 회원 조회 페이지 결과(Mybatis)

    @GetMapping("/selectResult")
    public ResponseEntity<UserDTO> selectResult(@RequestParam int userNum) {

        UserDTO user = selectService.findUserByNum(userNum);

        return ResponseEntity.ok(user);
    }



     // 2. 회원 조회 페이지 결과(JPA)

//    @GetMapping("/selectResult")
//    public String findUserById(@RequestParam int userNum, Model model) {
//        UserDTO user = userService.findUserById(userNum);  // 서비스에서 조회
//        model.addAttribute("user", user);
//        return "menu/selectResult";
//    }




//    // 3. 전체 회원 조회(JPA)
//
//    @GetMapping("/list")
//    public String findUserList(@PageableDefault(size=15) Pageable pageable, Model model) {
//        log.debug("pageable: {}", pageable);
//
//        Page<UserDTO> userList = userService.findUserList(pageable);
//
//        /* 설명. Page객체를 통해 PagingButtonInfo(front가 페이징 처리 버튼을 그리기 위한 재료를 지닌) 추출 */
//        PagingButtonInfo paging = Pagination.getPagingButtonInfo(userList);
//
//        model.addAttribute("userList", userList);
//        model.addAttribute("paging", paging);
//
//        return "menu/list";
//    }



    // 3. 전체 회원 조회 (Mybatis)
    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> findUserList() {
        List<UserDTO> userList = selectService.findAllUsers();

        return ResponseEntity.ok(userList);

    }



    // 4. 회원가입 기능

    @PostMapping("/regist")
    public ResponseEntity<UserDTO> registMenu(@RequestBody UserDTO newUser) {
        UserDTO savedUser = userService.registUser(newUser);
        return ResponseEntity.ok(savedUser);
    }



//    // 5. 회원정보 수정 <인적사항 수정> (JPA)
//
//
//    @PostMapping("/modify")
//    public String modifyMenu(UserDTO modifyMenu) {
//        userService.modifyMenu(modifyMenu);
//
//        return "redirect:/menu/selectResult?userNum=" + modifyMenu.getUserNum();
//    }


    // 5. 회원 정보  수정
    @PatchMapping("/{userNum}")
    public ResponseEntity<UserDTO> modifyMenu(
            @PathVariable int userNum,
            @RequestBody Map<String, Object> updates) {

        UserDTO updatedUser = userService.modifyMenu(userNum, updates);

        return ResponseEntity.ok(updatedUser);
    }

    // 6. 메시지 수신 여부 수정


    @PatchMapping("/message-allow/{userNum}")
    public ResponseEntity<UserDTO> modifyMessage(
            @PathVariable int userNum,
            @RequestBody Map<String, Object> updates) {

        UserDTO updatedUser = userService.modifyMessage(userNum, updates);
        return ResponseEntity.ok(updatedUser);
    }


    // 7. 신고 누적 or 하루 신고 가능 횟수 수정(관리자 권한)



    @PatchMapping("/report/{userNum}")
    public ResponseEntity<UserDTO> modifyReport(
            @PathVariable int userNum,
            @RequestBody Map<String, Object> updates) {

        UserDTO updatedUser = userService.modifyReport(userNum, updates);
        return ResponseEntity.ok(updatedUser);
    }


    // 8. 회원정보 삭제

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteUser(@RequestBody UserDTO userDTO) {
        int userNum = userDTO.getUserNum(); // Body에서 userNum 꺼냄
        userService.userDelete(userNum);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "회원이 삭제되었습니다.");
        response.put("deletedUserNum", userNum);

        return ResponseEntity.ok(response);
    }


}
