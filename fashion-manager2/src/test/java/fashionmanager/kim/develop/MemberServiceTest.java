package fashionmanager.kim.develop;

import fashionmanager.kim.develop.dto.*;
import fashionmanager.kim.develop.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @DisplayName("전체 회원 조회")
    @Test
    void testSelectMember(){
        Assertions.assertDoesNotThrow(
                ()->{
                    List<MemberDTO> members = memberService.selectMember();
                    System.out.println(members);
                }
        );
    }

    @DisplayName("회원 번호로 회원 조회")
    @ParameterizedTest
    @ValueSource(ints = {1,2})
    void testSelectMemberByNum(int memberNum){

        Assertions.assertDoesNotThrow(
                ()->{
                    MemberDTO member = memberService.selectMemberByNum(memberNum);
                    System.out.println(member);
                }
        );
    }

    @DisplayName("회원 권한 변경 테스트")
    @Test
    void testUpdateRight(){
        int memberNum = 8;
        int memberStateNum = 3;
        int result = memberService.updateRight(new UpdateRightDTO(memberNum, memberStateNum));
        Assertions.assertEquals(result,1);
    }

    @DisplayName("회원과 권한 동시 조회 테스트")
    @Test
    void testSelectMemberRight(){
        String memberId = "user06";
        Assertions.assertDoesNotThrow(
                ()->{
                    MemberRightDTO memberRight = memberService.selectMemberRightById(memberId);
                    System.out.println(memberRight);
                }
        );
    }
}


