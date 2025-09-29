package fashionmanager.baek.develop.dto;

import fashionmanager.kim.develop.dto.HashTagDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class SelectDetailReviewPostDTO {
    private int num;
    private String title;
    private String content;
    private int memberNum;
    private String memberName;
    private int good;
    private int cheer;
    private List<ItemDTO> items;
    private List<PhotoDTO> photos;
}
