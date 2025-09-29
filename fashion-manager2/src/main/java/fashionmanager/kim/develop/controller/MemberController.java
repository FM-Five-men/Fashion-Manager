package fashionmanager.kim.develop.controller;

import fashionmanager.kim.develop.dto.*;
import fashionmanager.kim.develop.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService ms;

    public MemberController(MemberService memberService) {
        this.ms = memberService;
    }


    @GetMapping("/selectmember")
    public ResponseEntity<List<MemberDTO>> selectMember(){
        List<MemberDTO> memberList = ms.selectMember();
        for (MemberDTO memberDTO : memberList){
            log.info("memberDTO:{}",memberDTO);
        }
        return ResponseEntity.ok(memberList);
    }

    @PostMapping("/insertadmin")
    public ResponseEntity<String> insertAdmin(@RequestBody InsertMemberDTO insertMemberDTO){
        List<MemberDTO> memberList = ms.selectMember();
        for (MemberDTO member : memberList){
            if(member.getMemberId().equals(insertMemberDTO.getMemberId())){
                log.info("이미 존재하는 관리자 아이디입니다.");
                return ResponseEntity.ok("이미 존재하는 관리자 아이디입니다.");
            }
        }

        int result = ms.insertAdmin(insertMemberDTO);
        if(result == 1){
            MemberDTO member = ms.selectMemberById(insertMemberDTO.getMemberId()); //관리자로 회원가입한 회원의 회원 번호를 확인하기 위한 코드
            int insertAdminNum = member.getMemberNum();
            AssignedRightDTO assignedRightDTO = new AssignedRightDTO();
            assignedRightDTO.setAssignedRightMemberStateNum(1);
            assignedRightDTO.setAssignedRightMemberNum(insertAdminNum);
            int result2 = ms.insertAdminRight(assignedRightDTO);
            if(result2 == 1){
                log.info("관리자 회원가입에 성공했습니다.");
                return ResponseEntity.ok("관리자 회원가입에 성공했습니다.");
            }else{
                log.info("2단계에서 관리자 회원가입에 실패했습니다.");
                return ResponseEntity.ok("관리자 회원가입에 실패했습니다.");
            }

        }else{
            log.info("1단계에서 관리자 회원가입에 실패했습니다.");
            return ResponseEntity.ok("관리자 회원가입에 실패했습니다.");
        }
    }

    @PostMapping("/updateright")
    public ResponseEntity<String> updateRight(@RequestBody UpdateRightDTO updateRightDTO){
        int result = ms.updateRight(updateRightDTO);
        if(result == 1){
            log.info("회원의 권한이 변경되었습니다.");
            return ResponseEntity.ok("회원의 권한이 변경되었습니다.");
        }else{
            log.info("회원의 권한 변경이 실패하였습니다.");
            return ResponseEntity.ok("회원의 권한 변경이 실패하였습니다.");
        }
    }

    @PostMapping("/memberlogin")
    public ResponseEntity<String> memberLogin(String memberId, String memberPwd){
        String result = ms.memberLogin(memberId, memberPwd);

        return ResponseEntity.ok(result);
    }
}
