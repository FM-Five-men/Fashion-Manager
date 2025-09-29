package fashionmanager.kim.develop.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SelectMassageDTO {
    private String title;
    private String content;
    private LocalDateTime date;
    private LocalTime expDate;
    private String senderId;
    private String receiverId;
    private String category;
    private boolean messageConfirmed;
}
