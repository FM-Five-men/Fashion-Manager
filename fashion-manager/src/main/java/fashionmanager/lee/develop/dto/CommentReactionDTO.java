package fashionmanager.lee.develop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentReactionDTO {
    private Integer memberNum;
    private String reactionType;
}