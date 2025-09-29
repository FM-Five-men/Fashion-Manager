package fashionmanager.kim.develop.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InsertReportDTO {
    private LocalDateTime reportDate;
    private String reportContent;
    private String reportState;
    private int reportCategoryNum;
    private String reportCategoryName;
    private int fashionPostNum;
    private int reviewPostNum;
    private int mentoringPostNum;
    private int commentNum;
}
