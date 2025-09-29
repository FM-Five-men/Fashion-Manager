package fashionmanager.baek.develop.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
public class FashionModifyResponseDTO {
    private int num;
    private String title;
    private String content;
    private int member_num;
    private List<Integer> hashtag;
    private List<Integer> items;
}
