package fashionmanager.baek.develop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "review_post")
@Data
@Getter
@Setter
public class ReviewPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "good")
    private int good;

    @Column(name = "cheer")
    private int cheer;

    @Column(name = "member_num")
    private int memberNum;

    @Column(name = "review_category_num")
    private int reviewCategoryNum;

}
