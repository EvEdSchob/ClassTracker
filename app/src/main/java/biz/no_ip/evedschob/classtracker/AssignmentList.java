package biz.no_ip.evedschob.classtracker;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Evan on 11/21/2017.
 */

/**
 * Previous iteration of AssignmentList left in for completeness.
 * Now exists as an inner class of Course.
 * This was done for a number of reasons:
 * 1. Each Course needs only one assignment list
 * 2. Each course MUST have an assignment list, even if empty
 * 3. An assignment list should not exist without a course
 * 4. Maintaining Course and AssignmentList as extant classes impeded
 *    their usage in EditAssignmentFragment
 *
 * With further app development this may eventually become an interface.
 * It could be said that from the app's perspective a Course IS an
 * assignment list or perhaps a graded list, taking into better account
 * graded that work that does not necessarily constitute an assignment
 * E.g. Tests, quizzes, exams, etc.
 * This might also allow for a list of lists that could better facilitate
 * the calculation of weighted averages, something that this app in its
 * current state does not do.*/

//public class AssignmentList{
//    private List<Assignment> mAssignments;
//
//    private double mTotalPointsEarned;
//    private double mTotalPointsPossible;
//    private double mCoursePercentage;
//
//
//    public AssignmentList(){
//        mAssignments = new ArrayList<>();
//    }
//
//    public void addAssignment(Assignment assignment){
//        mAssignments.add(assignment);
//
//        mTotalPointsEarned += assignment.getPointsEarned();
//        mTotalPointsPossible += assignment.getPointsPossible();
//
//        mCoursePercentage = (mTotalPointsEarned/mTotalPointsPossible)*100;
//
//    }
//
//    public List<Assignment> getAssignments(){
//        return mAssignments;
//    }
//
//    public Assignment getAssignment(UUID id) {
//        for (Assignment assignment : mAssignments) {
//            if (assignment.getAssignmentId().equals(id)){
//                return assignment;
//            }
//        }
//        return null;
//    }
//
//    public double getCoursePercentage() {
//        return mCoursePercentage;
//    }
//
//
//}
