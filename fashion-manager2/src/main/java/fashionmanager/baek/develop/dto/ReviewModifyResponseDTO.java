package fashionmanager.baek.develop.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ReviewModifyResponseDTO {
    private int num;
    private String title;
    private String content;
    private int memberNum;
    private List<Integer> items;
    private int reviewCategoryNum;
}
