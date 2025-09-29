package fashionmanager.kim.develop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "hash_tag")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HashTag {

    @Id
    @Column(name = "num")
    private int hashTagNum;

    @Column(name = "name")
    private String hashTagName;
}
