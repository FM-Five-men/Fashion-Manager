package fashionmanager.kim.develop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="report")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Report {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "num")
    private int reportNum;

    @Column(name="date")
    private LocalDateTime reportDate;

    @Column(name="content")
    private String reportContent;

    @Column(name = "state")
    private String reportState;

    @Column(name = "report_category_num")
    private int reportCategoryNum;

    @Column(name = "fashion_post_num")
    private Integer fashionPostNum;

    @Column(name="review_post_num")
    private Integer reviewPostNum;

    @Column(name = "mentoring_post_num")
    private Integer mentoringPostNum;

    @Column(name = "comment_num")
    private Integer commentNum;
}
