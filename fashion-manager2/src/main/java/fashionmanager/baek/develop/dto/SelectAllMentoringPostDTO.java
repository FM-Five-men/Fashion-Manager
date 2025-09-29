package fashionmanager.baek.develop.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SelectAllMentoringPostDTO {
    private int num;
    private String title;
    private int authorNum;
    private boolean finish;
}
