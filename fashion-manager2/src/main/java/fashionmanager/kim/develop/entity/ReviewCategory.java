package fashionmanager.kim.develop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="review_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewCategory {

    @Id
    @Column(name="num")
    private int reviewCategoryNum;

    @Column(name="name")
    private String reviewCategoryName;
}
