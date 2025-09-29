package fashionmanager.baek.develop.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ReviewRegistRequestDTO {
    private int num;
    private String title;
    private String content;
    private int good = 0;
    private int cheer = 0;
    private List<Integer> items;
    private int memberNum;
    private int reviewCategoryNum;
}
