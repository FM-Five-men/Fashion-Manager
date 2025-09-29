package fashionmanager.kim.develop.dto;

import lombok.*;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private int memberNum;
    private String memberId;
    private String memberPwd;
    private String memberEmail;
    private String memberName;
    private int memberAge;
    private char memberGender;
    private int memberHeight;
    private int memberWeight;
    private String memberStatus;
    private int memberReportCount;
    private int memberDailyReportCount;
    private int memberGoodCount;
    private int memberMonthlyGoodCount;
    private int memberCheerCount;
    private boolean memberMessageAllow;

}
