package fashionmanager.kim.develop.dto;

import lombok.*;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HashTagAndPostDTO {
    private String hashTagName;

    private String fashionPostTitle;
    private String fashionPostContent;
    private int fashionPostGood;
    private int fashionPostCheer;
    private int fashionPostTemp;
    private int fashionPostMemberNum;
}
