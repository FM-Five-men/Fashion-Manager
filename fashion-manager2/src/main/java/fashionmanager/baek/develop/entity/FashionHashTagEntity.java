package fashionmanager.baek.develop.entity;

import fashionmanager.baek.develop.entity.pk.FashionHashTagPK;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fashion_hashtag")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FashionHashTagEntity {
    @EmbeddedId
    private FashionHashTagPK fashionHashTagPK;

}
