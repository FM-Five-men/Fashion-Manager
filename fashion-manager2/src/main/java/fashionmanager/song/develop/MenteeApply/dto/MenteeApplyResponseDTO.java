package fashionmanager.song.develop.menteeApply.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MenteeApplyResponseDTO {

    private Integer num;
    private String content;
    private String accept;
    private Integer mentoringPostNum;
    private Integer memberNum;

    private String memberName;

    //  이미지 관련 코드
    private List<String> photoPaths;
}
