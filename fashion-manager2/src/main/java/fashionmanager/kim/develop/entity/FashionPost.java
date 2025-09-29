package fashionmanager.kim.develop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "fashionPost")
@Table(name = "fashion_post")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FashionPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num")
    private int fashionPostNum;

    @Column(name = "title")
    private String fashionPostTitle;

    @Column(name = "content")
    private String fashionPostContent;

    @Column(name = "good")
    private int fashionPostGood;

    @Column(name = "cheer")
    private int fashionPostCheer;

    @Column(name = "temp")
    private int fashionPostTemp;

    @Column(name = "member_num")
    private int fashionPostMemberNum;
}
