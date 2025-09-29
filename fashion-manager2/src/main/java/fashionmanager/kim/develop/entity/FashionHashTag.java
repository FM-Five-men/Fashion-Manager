package fashionmanager.kim.develop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fashion_hashtag")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FashionHashTagPK.class)
public class FashionHashTag {

    @Id
    @Column(name = "post_num")
    private int fashionHashTagPostNum;

    @Id
    @Column(name = "tag_num")
    private int fashionHashTagTagNum;
}
