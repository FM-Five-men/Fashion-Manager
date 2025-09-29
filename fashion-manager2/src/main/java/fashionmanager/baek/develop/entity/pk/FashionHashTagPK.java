package fashionmanager.baek.develop.entity.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@ToString
public class FashionHashTagPK implements Serializable {
    @Column(name = "post_num")
    private int postNum;

    @Column(name = "tag_num")
    private int tagNum;

    public FashionHashTagPK() {}

    public FashionHashTagPK(int postNum, int tagNum) {
        this.postNum = postNum;
        this.tagNum = tagNum;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FashionHashTagPK that = (FashionHashTagPK) o;
        return postNum == that.postNum && tagNum == that.tagNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postNum, tagNum);
    }
}
