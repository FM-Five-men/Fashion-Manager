package fashionmanager.baek.develop.dto;

import fashionmanager.kim.develop.dto.HashTagDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class SelectDetailMentoringPostDTO {
    private int num;
    private String title;
    private String content;
    private int authorNum;
    private String authorName;
    private boolean finish;
    private List<PhotoDTO> photos;
}
