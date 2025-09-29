package fashionmanager.song.develop.common;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num")
    private int memberNum;

    @Column(name = "id")
    private String memberId;

    @Column(name = "pwd")
    private String memberPwd;

    @Column(name = "email")
    private String memberEmail;

    @Column(name = "name")
    private String memberName;

    @Column(name = "age")
    private int memberAge;

    @Column(name = "gender")
    private char memberGender;

    @Column(name = "height")
    private int memberHeight;

    @Column(name = "weight")
    private int memberWeight;

    @Column(name = "status")
    private String memberStatus;

    @Column(name = "report_count")
    private int memberReportCount;

    @Column(name = "daily_report_count")
    private int memberDailyReportCount;

    @Column(name = "good_count")
    private int memberGoodCount;

    @Column(name = "monthly_good_count")
    private int memberMonthlyGoodCount;

    @Column(name = "cheer_count")
    private int memberCheerCount;

    @Column(name = "message_allow")
    private boolean memberMessageAllow;
}
