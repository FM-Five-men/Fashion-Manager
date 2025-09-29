package fashionmanager.lee.develop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "COMMENT")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num")
    private Integer num;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "good", nullable = false)
    private int good = 0;

    @Column(name = "cheer", nullable = false)
    private int cheer = 0;

    @Column(name = "member_num", nullable = false)
    private Integer memberNum;

    @Column(name = "mentoring_post_num")
    private Integer mentoringPostNum;

    @Column(name = "review_post_num")
    private Integer reviewPostNum;

    @Column(name = "fashion_post_num")
    private Integer fashionPostNum;

    public Comment(String content, Integer memberNum, Integer postNum, String postType) {
        this.content = content;
        this.memberNum = memberNum;
        switch (postType) {
            case "fashion" -> this.fashionPostNum = postNum;
            case "review" -> this.reviewPostNum = postNum;
            case "mentoring" -> this.mentoringPostNum = postNum;
            default -> throw new IllegalArgumentException("Invalid post type: " + postType);
        }
    }
}