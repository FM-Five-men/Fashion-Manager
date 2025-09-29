package fashionmanager.kim.develop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "assigned_right")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AssignedRightPK.class)
public class AssignedRight {

    @Id
    @Column(name = "member_state_num")
    private int assignedRightMemberStateNum;

    @Id
    @Column(name = "member_num")
    private int assignedRightMemberNum;
}
