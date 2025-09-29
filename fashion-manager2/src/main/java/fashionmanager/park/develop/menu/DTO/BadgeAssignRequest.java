package fashionmanager.park.develop.menu.DTO;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BadgeAssignRequest {
    private int userNum;
    private int badgeNum;
}