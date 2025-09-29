package fashionmanager.kim.develop.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private int messageNum;
    private String messageTitle;
    private String messageContent;
    private LocalDateTime messageDate;
    private LocalDateTime messageExpDate;
    private int messageSenderNum;
    private int messageReceiverNum;
    private int messageCategoryNum;
    private boolean messageConfirmed;
}
