package fashionmanager.park.develop.menu.DTO;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    private int userNum;
    private String userId;
    private String userPwd;
    private String userEmail;
    private String userName;
    private int userAge;
    private String userGender;
    private int userMessageAllow;
    private int ReportCount;
    private int DailyReportCount;

    private List<BadgeDTO> badges = new ArrayList<>();



}
