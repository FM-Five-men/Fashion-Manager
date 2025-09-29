package fashionmanager.baek.develop.entity;

import fashionmanager.baek.develop.entity.pk.FashionPostItemPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "post_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FashionPostItemEntity {
    @EmbeddedId
    private FashionPostItemPK fashionPostItemPK;

}
