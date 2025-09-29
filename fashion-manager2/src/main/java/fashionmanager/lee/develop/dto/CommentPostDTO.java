package fashionmanager.lee.develop.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentPostDTO {
    private String content;
    private int memberNum;
    private int postNum;
    private String postType; // "fashion", "review", "mentoring"
}
