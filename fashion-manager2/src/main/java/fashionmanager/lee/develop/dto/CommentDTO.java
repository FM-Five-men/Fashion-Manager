package fashionmanager.lee.develop.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private int num;
    private String content;
    private int good;
    private int cheer;
    private int memberNum;
    private String memberName; // MEMBER 테이블과 JOIN하여 가져올 사용자 이름
}
