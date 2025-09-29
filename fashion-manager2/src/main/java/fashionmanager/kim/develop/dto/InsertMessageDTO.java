package fashionmanager.kim.develop.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InsertMessageDTO {
    private String title;
    private String content;
    private LocalDateTime date;
    private LocalDateTime expDate;
    private int senderNum;
    private String senderName;
    private int receiverNum;
    private String receiverName;
    private int categoryNum;
}
