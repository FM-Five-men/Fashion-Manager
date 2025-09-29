package fashionmanager.kim.develop.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BlacklistDTO {
    private int blacklistNum;
    private String blacklistReason;
    private LocalDateTime blacklistStartDate;
    private LocalDateTime blacklistExpDate;
    private int blacklistMemberNum;
}
