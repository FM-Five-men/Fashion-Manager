package fashionmanager.kim.develop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="photo_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PhotoCategory {
    @Id
    @Column(name = "num")
    private int photoCategoryNum;

    @Column(name = "name")
    private String photoCategoryName;
}
