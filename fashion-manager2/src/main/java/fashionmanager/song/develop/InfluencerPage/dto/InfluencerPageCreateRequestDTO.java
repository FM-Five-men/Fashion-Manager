package fashionmanager.song.develop.influencerPage.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InfluencerPageCreateRequestDTO {

    private int num;
    private String title;
    private String content;
    private String insta;
    private String phone;
    private int memberNum;


    private String memberName;


    //  이미지 관련 코드
    private List<String> photoPaths;
}
