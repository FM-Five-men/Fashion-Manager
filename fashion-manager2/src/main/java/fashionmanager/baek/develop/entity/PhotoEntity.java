package fashionmanager.baek.develop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "photo")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;

    @Column(name = "name")
    private String name;

    @Column(name = "post_num")
    private int postNum;

    @Column(name = "path")
    private String path;

    @Column(name = "photo_category_num")
    private int photoCategoryNum;
}
