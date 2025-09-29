package fashionmanager.kim.develop;

import fashionmanager.kim.develop.dto.InsertMessageDTO;
import fashionmanager.kim.develop.dto.MessageDTO;
import fashionmanager.kim.develop.dto.SelectMassageDTO;
import fashionmanager.kim.develop.service.MessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @DisplayName("전체 쪽지 목록 조회 테스트")
    @Test
    void testSelectAllMessages(){
        Assertions.assertDoesNotThrow(
                ()->{
                    List<MessageDTO> messages = messageService.selectAllMessages();
                    messages.forEach(System.out::println);
                }
        );

    }

    @DisplayName("보낸사람 기준 쪽지 목록 조회 테스트")
    @Test
    void testSelectSenderMessages(){
        String senderId = "user02";

        Assertions.assertDoesNotThrow(
                ()->{
                    List<SelectMassageDTO> messages = messageService.selectSenderMessage(senderId);
                    messages.forEach(System.out::println);
                }
        );
    }

    @DisplayName("받은사람 기준 쪽지 목록 조회 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"user01","user02"})
    void testSelectReceiverMessages(String receiverId){
        Assertions.assertDoesNotThrow(
                ()->{
                    List<SelectMassageDTO> messages = messageService.selectReceiverMessage(receiverId);
                    messages.forEach(System.out::println);
                }
        );
    }

    @DisplayName("메세지 송신 테스트")
    @ParameterizedTest
    @CsvSource({"4,user01","6,user03"})
    void testInsertMessage(int receiverNum, String receiverId){

        InsertMessageDTO insertMessageDTO = new InsertMessageDTO();
        insertMessageDTO.setTitle("반갑습니다.");
        insertMessageDTO.setContent("제가 찾던 패션이었습니다. 혹시 패션을 알려주실 수 있나요?");
        insertMessageDTO.setDate(LocalDateTime.of(2025, 9, 10, 10, 30,00));
        insertMessageDTO.setExpDate(LocalDateTime.of(2025, 9, 10, 10, 30,00));
        insertMessageDTO.setSenderNum(5);
        insertMessageDTO.setSenderName("user02");
        insertMessageDTO.setReceiverNum(receiverNum);
        insertMessageDTO.setReceiverName(receiverId);
        insertMessageDTO.setCategoryNum(1);


        int result = messageService.insertMessage(insertMessageDTO);

        Assertions.assertEquals(1, result, "상대방이 메세지 수신을 거부했습니다.");
    }

    @DisplayName("메시지 삭제 테스트")
    @ParameterizedTest
    @ValueSource(ints = {6,7})
    void testDeleteMessage(int num){
        int result = messageService.deleteMessage(num);
        Assertions.assertTrue(1 == result);
    }
}
