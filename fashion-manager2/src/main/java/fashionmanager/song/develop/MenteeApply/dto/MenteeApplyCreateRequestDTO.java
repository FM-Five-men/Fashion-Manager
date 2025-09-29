package fashionmanager.song.develop.menteeApply.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MenteeApplyCreateRequestDTO {
    // int -> Integer : null 가능하게
    private Integer num;
    private String content;
    private String accept;
    private Integer mentoringPostNum;
    private Integer memberNum;

    private String memberName;

    //  이미지 관련 코드
    private List<String> photoPaths;

}
