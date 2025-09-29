package fashionmanager.baek.develop.dto;

import fashionmanager.kim.develop.dto.HashTagDTO;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SelectDetailFashionPostDTO {
    private int num;
    private String title;
    private String content;
    private int memberNum;
    private String memberName;
    private int good;
    private int cheer;
    private double temp;
    private List<ItemDTO> items;
    private List<HashTagDTO> hashtags;
    private List<PhotoDTO> photos;
}
