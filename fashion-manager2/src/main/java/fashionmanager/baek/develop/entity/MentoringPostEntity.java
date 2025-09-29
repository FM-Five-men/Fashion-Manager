package fashionmanager.baek.develop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mentoring_post")
@Data
@Getter
@Setter
public class MentoringPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num")
    private int num;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "finish")
    private boolean finish;

    @Column(name = "author_num")
    private int authorNum;
}
