package fashionmanager.kim.develop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="message_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageCategory {

    @Id
    @Column(name="num")
    private int messageCategoryNum;

    @Column(name="name")
    private String messageCategoryName;
}
