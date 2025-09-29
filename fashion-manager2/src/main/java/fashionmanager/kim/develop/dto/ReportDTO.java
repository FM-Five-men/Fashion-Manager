package fashionmanager.kim.develop.dto;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    private int reportNum;
    private LocalDateTime reportDate;
    private String reportContent;
    private String reportState;
    private int reportCategoryNum;
    private Integer fashionPostNum;
    private Integer reviewPostNum;
    private Integer mentoringPostNum;
    private Integer commentNum;
}
