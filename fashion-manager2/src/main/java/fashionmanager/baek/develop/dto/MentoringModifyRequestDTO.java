package fashionmanager.baek.develop.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class MentoringModifyRequestDTO {
    private int num;
    private String title;
    private String content;
    private boolean finish;
    private int authorNum;
}
