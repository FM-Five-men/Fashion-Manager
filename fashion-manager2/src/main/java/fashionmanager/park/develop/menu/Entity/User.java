package fashionmanager.park.develop.menu.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB의 AUTO_INCREMENT 활용
    @Column(name="num")
    private int userNum;

    @Column(name="id")
    private String userId;

    @Column(name="pwd")
    private String userPwd;

    @Column(name="email")
    private String userEmail;

    @Column(name="NAME")
    private String userName;

    @Column(name="age")
    private int userAge;

    @Column(name="gender")
    private String userGender;

    @Column(nullable = false)
    private String status = "ACTIVE";

    @Column(name="message_allow")
    private int userMessageAllow;

    @Column(name="report_count")
    private int ReportCount;

    @Column(name="daily_report_count")
    private int DailyReportCount;



    // 뱃지와 다대다 관계
    @ManyToMany
    @JoinTable(
            name = "assigned_badge",  // 매핑 테이블
            joinColumns = @JoinColumn(name = "member_num"),    // User FK
            inverseJoinColumns = @JoinColumn(name = "badge_num") // Badge FK
    )

    // 뱃지 수여
    private List<Badge> badges = new ArrayList<>();



    // 편의 메소드
    public void addBadge(Badge badge) {
        this.badges.add(badge);
        badge.getUsers().add(this); // 반대쪽도 업데이트
    }



}
