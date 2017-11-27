package biz.no_ip.evedschob.classtracker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Evan on 11/21/2017.
 */

public class AssignmentList {
    private List<Assignment> mAssignments;

    private double mTotalPointsEarned;
    private double mTotalPointsPossible;
    private double mCoursePercentage;

    public AssignmentList(){
        mAssignments = new ArrayList<>();
    }

    public void addAssignment(Assignment assignment){
        mAssignments.add(assignment);

        mTotalPointsEarned += assignment.getPointsEarned();
        mTotalPointsPossible += assignment.getPointsPossible();

        mCoursePercentage = (mTotalPointsEarned/mTotalPointsPossible)*100;

    }

    public List<Assignment> getAssignments(){
        return mAssignments;
    }

    public Assignment getAssignment(UUID id) {
        for (Assignment assignment : mAssignments) {
            if (assignment.getAssignmentId().equals(id)){
                return assignment;
            }
        }
        return null;
    }

    public double getCoursePercentage() {
        return mCoursePercentage;
    }
}
