package fashionmanager.kim.develop.controller;

import fashionmanager.kim.develop.dto.InsertMessageDTO;
import fashionmanager.kim.develop.dto.MessageDTO;
import fashionmanager.kim.develop.dto.SelectMassageDTO;
import fashionmanager.kim.develop.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService ms;

    public MessageController(MessageService messageService) {
        this.ms = messageService;
    }

    @GetMapping("/selectallmessages")
    public ResponseEntity<List<MessageDTO>> selectAllMessages() {
        List<MessageDTO> messageList = ms.selectAllMessages();
        for (MessageDTO messageDTO : messageList) {
            log.info("messageDTO: {}", messageDTO);
        }

        return ResponseEntity.ok(messageList);
    }

    @GetMapping("/selectsendermessage")
    public ResponseEntity<List<SelectMassageDTO>> selectSenderMessage(String senderId) {
        List<SelectMassageDTO> messageList = ms.selectSenderMessage(senderId);
        for (SelectMassageDTO messageDTO : messageList) {
            log.info("messageDTO: {}", messageDTO);
        }

        return ResponseEntity.ok(messageList);
    }

    @GetMapping("/selectreceivermessage")
    public ResponseEntity<List<SelectMassageDTO>> selectReceiverMessage(String receiverId) {
        List<SelectMassageDTO> messageList = ms.selectReceiverMessage(receiverId);
        for (SelectMassageDTO messageDTO : messageList) {
            log.info("messageDTO: {}", messageDTO);
        }

        return ResponseEntity.ok(messageList);
    }

    @PostMapping("/insertmessage")
    public ResponseEntity<String> insertMessage(@RequestBody InsertMessageDTO insertMessageDTO){
        int result = ms.insertMessage(insertMessageDTO);
        if(result == 1){
            log.info("메시지가 보내졌습니다.");
            return ResponseEntity.ok("메시지가 보내졌습니다.");
        }else{
            log.info("메시지를 보내는데, 실패했습니다.");
            return ResponseEntity.ok("메시지를 보내는데, 실패했습니다.");
        }
    }

    @PostMapping("/deletemessage")
    public ResponseEntity<String> deleteMessage(int messageNum){
        int result = ms.deleteMessage(messageNum);
        if(result == 1){
            log.info("메시지를 삭제하였습니다.");
            return ResponseEntity.ok("메시지를 삭제하였습니다.");
        }else{
            log.info("메시지 삭제에 실패했습니다.");
            return ResponseEntity.ok("메시지 삭제에 실패했습니다.");
        }
    }

}
