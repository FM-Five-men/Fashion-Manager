package fashionmanager.baek.develop.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class FashionModifyRequestDTO {
    private int num;
    private String title;
    private String content;
    private int member_num;
    private List<Integer> hashtag;
    private List<Integer> items;
}
