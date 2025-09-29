package fashionmanager.kim.develop.service;

import fashionmanager.kim.develop.dto.InsertMessageDTO;
import fashionmanager.kim.develop.dto.MemberDTO;
import fashionmanager.kim.develop.dto.MessageDTO;
import fashionmanager.kim.develop.dto.SelectMassageDTO;
import fashionmanager.kim.develop.entity.Message;
import fashionmanager.kim.develop.mapper.MemberMapper;
import fashionmanager.kim.develop.mapper.MessageMapper;
import fashionmanager.kim.develop.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class MessageService {

    private MessageMapper messageMapper;
    private MemberMapper memberMapper;

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageMapper messageMapper, MemberMapper memberMapper
                        , MessageRepository messageRepository) {
        this.messageMapper = messageMapper;
        this.memberMapper = memberMapper;

        this.messageRepository = messageRepository;
    }

    public List<MessageDTO> selectAllMessages() {
        return messageMapper.selectAllMessages();
    }

    public List<SelectMassageDTO> selectSenderMessage(String senderId) {
        return messageMapper.selectSenderMessage(senderId);
    }

    public List<SelectMassageDTO> selectReceiverMessage(String receiverId) {
        return messageMapper.selectReceiverMessage(receiverId);
    }

    public int insertMessage(InsertMessageDTO insertMessageDTO) {
        MemberDTO resultMember = memberMapper.selectMessageAllow(insertMessageDTO.getReceiverName());

        if(resultMember.isMemberMessageAllow()){
            Message message = new Message();
            message.setMessageTitle(insertMessageDTO.getTitle());
            message.setMessageContent(insertMessageDTO.getContent());
            message.setMessageDate(insertMessageDTO.getDate());
            message.setMessageExpDate(insertMessageDTO.getExpDate());
            message.setMessageSenderNum(insertMessageDTO.getSenderNum());
            message.setMessageReceiverNum(insertMessageDTO.getReceiverNum());
            message.setMessageCategoryNum(insertMessageDTO.getCategoryNum());

            messageRepository.save(message);
            log.info("메세지를 보냈습니다.");
            return 1;
        }else{
            log.info("상대방이 메세지 수신을 거부했습니다.");
            return 0;
        }
    }

    @Transactional
    public int deleteMessage(int messageNum) {
         int result = messageRepository.deleteMessage(messageNum);
         if(result == 1){
             return 1;
         }else{
             return 0;
         }
    }
}
