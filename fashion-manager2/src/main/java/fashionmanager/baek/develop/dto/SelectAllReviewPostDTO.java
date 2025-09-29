package fashionmanager.baek.develop.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SelectAllReviewPostDTO {
    private int num;
    private String title;
    private int memberNum;
    private int good;
    private int cheer;
}
