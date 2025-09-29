package fashionmanager.baek.develop.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MentoringRegistResponseDTO {
    private int num;
    private String title;
    private String content;
    private int authorNum;
    private boolean finish;
}
