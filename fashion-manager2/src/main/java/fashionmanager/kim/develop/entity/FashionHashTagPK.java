package fashionmanager.kim.develop.entity;

import java.util.Objects;

public class FashionHashTagPK {
    private int fashionHashTagPostNum;
    private int fashionHashTagTagNum;

    public FashionHashTagPK() {
    }

    public FashionHashTagPK(int fashionHashTagPostNum, int fashionHashTagTagNum) {
        this.fashionHashTagPostNum = fashionHashTagPostNum;
        this.fashionHashTagTagNum = fashionHashTagTagNum;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FashionHashTagPK fashionHashTagPK = (FashionHashTagPK) o;
        return fashionHashTagPostNum == fashionHashTagPK.fashionHashTagPostNum && fashionHashTagTagNum == fashionHashTagPK.fashionHashTagTagNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fashionHashTagPostNum, fashionHashTagTagNum);
    }

}
