package fashionmanager.kim.develop.entity;

import java.util.Objects;

public class AssignedRightPK {
    private int assignedRightMemberStateNum;
    private int assignedRightMemberNum;

    public AssignedRightPK() {
    }

    public AssignedRightPK(int assignedRightMemberStateNum, int assignedRightMemberNum) {
        this.assignedRightMemberStateNum = assignedRightMemberStateNum;
        this.assignedRightMemberNum = assignedRightMemberNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignedRightPK assignedRightPK = (AssignedRightPK) o;
        return assignedRightMemberStateNum == assignedRightPK.assignedRightMemberStateNum &&
                assignedRightMemberNum == assignedRightPK.assignedRightMemberNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignedRightMemberStateNum, assignedRightMemberNum);
    }
}
