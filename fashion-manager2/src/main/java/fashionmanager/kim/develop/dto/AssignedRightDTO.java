package fashionmanager.kim.develop.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AssignedRightDTO {
    private int assignedRightMemberStateNum;
    private int assignedRightMemberNum;
}
