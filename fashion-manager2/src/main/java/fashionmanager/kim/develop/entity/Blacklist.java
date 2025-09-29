package fashionmanager.kim.develop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "blacklist")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Blacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num")
    private int blacklistNum;

    @Column(name = "reason")
    private String blacklistReason;

    @Column(name = "start_date")
    private LocalDateTime blacklistStartDate;

    @Column(name = "exp_date")
    private LocalDateTime blacklistExpDate;

    @Column(name = "member_num")
    private int blacklistMemberNum;
}
