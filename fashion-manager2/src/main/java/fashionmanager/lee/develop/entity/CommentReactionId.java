package fashionmanager.lee.develop.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CommentReactionId implements Serializable {
    private Integer memberNum;
    private Integer commentNum;
}