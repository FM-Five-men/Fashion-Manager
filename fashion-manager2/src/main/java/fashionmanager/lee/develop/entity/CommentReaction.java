package fashionmanager.lee.develop.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "COMMENT_REACTION")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CommentReactionId.class)
public class CommentReaction {
    @Id
    @Column(name = "member_num")
    private Integer memberNum;

    @Id
    @Column(name = "comment_num")
    private Integer commentNum;

    @Column(name = "reaction_type", nullable = false)
    private String reactionType;
}