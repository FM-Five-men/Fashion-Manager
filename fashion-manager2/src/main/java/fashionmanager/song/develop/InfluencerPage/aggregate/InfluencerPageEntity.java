package fashionmanager.song.develop.influencerPage.aggregate;

import fashionmanager.song.develop.common.MemberEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "influencer_page")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfluencerPageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // auto increament
    @Column(name = "num")
    private int num;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "insta")
    private String insta;

    @Column(name = "phone")
    private String phone;

    @Column(name = "member_num", nullable = false) // fk라서 null 불가
    private int memberNum;

    // join을 위한 ManyToOne과 JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_num", insertable = false, updatable = false)
    private MemberEntity member;

}
