package fashionmanager.kim.develop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="report_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReportCategory {

    @Id
    @Column(name="num")
    private int reportCategoryNum;

    @Column(name="name")
    private String reportCategoryName;
}
