package fashionmanager.baek.develop.entity.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
public class FashionPostItemPK {
    @Column(name = "post_num")
    private int postNum;
    @Column(name = "item_num")
    private int itemNum;

    public FashionPostItemPK() {
    }

    public FashionPostItemPK(int postNum, int itemNum) {
        this.postNum = postNum;
        this.itemNum = itemNum;
    }
}
