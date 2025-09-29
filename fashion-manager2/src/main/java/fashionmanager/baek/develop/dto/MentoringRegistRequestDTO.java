package fashionmanager.baek.develop.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
public class MentoringRegistRequestDTO {
    private int num;
    private String title;
    private String content;
    private boolean finish = false;
    private int authorNum;
}
