package biz.no_ip.evedschob.classtracker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Evan on 11/20/2017.
 */

public class Course {

    private String mCRN;
    private String mSubject;
    private String mSection;
    private String mCourseName;
    private boolean[] mDays;
    private String mStartTime;
    private String mEndTime;

    private double mGrade;

    private AssignmentList mAssignmentList = new AssignmentList();

    public Course(String crn, String sub, String sec, String name,
                  boolean[] days, String startTime, String endTime) {
        mCRN = crn;
        mSubject = sub;
        mSection = sec;
        mCourseName = name;
        mDays = days;
        mStartTime = startTime;
        mEndTime = endTime;

        mGrade = calculateGrade(mAssignmentList.getCoursePercentage());
    }

    //*****************************
    //Private Methods
    //*****************************
    private double calculateGrade(double percent) {
        if (percent >= 90) {mGrade = 4.0;
        } else if (percent >= 85) {mGrade = 3.5;
        } else if (percent >= 80) {mGrade = 3.0;
        } else if (percent >= 75) {mGrade = 2.5;
        } else if (percent >= 70) {mGrade = 2.0;
        } else if (percent >= 65) {mGrade = 1.5;
        } else if (percent >= 60) {mGrade = 1.0;
        } else {mGrade = 0.0;
        }return mGrade;
    }

    //*****************************
    //Public Methods
    //*****************************
    public void updateCourse(){
        mAssignmentList.updateAssignmentList();
        mGrade = calculateGrade(mAssignmentList.getCoursePercentage());
    }

    public void addAssignment(Assignment assignment) {
        mAssignmentList.addAssignment(assignment);
        calculateGrade(mAssignmentList.getCoursePercentage());
    }

    public void removeAssignment(Assignment assignment){
        mAssignmentList.removeAssignment(assignment);
        calculateGrade(mAssignmentList.getCoursePercentage());
    }

    public Assignment getAssignment(UUID id){
        return mAssignmentList.getAssignment(id);
    }

    public List<Assignment> getAssignmentList() {
        return mAssignmentList.getAssignments();
    }

    public String getDaysAsString() {
        String outputString = "";
        if (mDays[0]) {outputString += "M, ";
        }if (mDays[1]) {outputString += "T, ";
        }if (mDays[2]) {outputString += "W, ";
        }if (mDays[3]) {outputString += "R, ";
        }if (mDays[4]) {outputString += "F, ";
        }if (mDays[5]) {outputString += "S, ";}
        return outputString;
    }

    public static String getTimeAsFormattedString(String time){
        String[] hourMinute = time.split(":");
        String AmPm = "AM";
        String sMinute;
        int hour = Integer.valueOf(hourMinute[0]);
        int minute = Integer.valueOf(hourMinute[1]);
        if (hour > 12 ) {
            hour = hour - 12;
            AmPm = "PM";
        }
        if (minute == 0){
            sMinute = "00";
        } else {
            sMinute = String.valueOf(minute);
        }

        return String.valueOf(hour + ":" + sMinute + " " + AmPm);
    }

    //*****************************
    //Getters & Setters
    //*****************************
    public String getCRN() {
        return mCRN;
    }

    public String getSubject() {
        return mSubject;
    }

    public String getSection() {
        return mSection;
    }

    public String getCourseName() {
        return mCourseName;
    }

    public boolean[] getDays() {
        return mDays;
    }

    public double getGrade() {
        return mGrade;
    }

    public void setCRN(String CRN) {
        mCRN = CRN;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public void setSection(String section) {
        mSection = section;
    }

    public void setCourseName(String courseName) {
        mCourseName = courseName;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public void setDays(boolean[] days) {
        mDays = days;
    }

    //*****************************
    //Inner class
    //*****************************
    private class AssignmentList {
        private List<Assignment> mAssignments;

        private double mTotalPointsEarned;
        private double mTotalPointsPossible;
        private double mCoursePercentage;


        private AssignmentList() {
            mAssignments = new ArrayList<>();
        }

        private void addAssignment(Assignment assignment) {
            mAssignments.add(assignment);

            mTotalPointsEarned += assignment.getPointsEarned();
            mTotalPointsPossible += assignment.getPointsPossible();

            mCoursePercentage = (mTotalPointsEarned / mTotalPointsPossible) * 100;

        }

        private void removeAssignment(Assignment assignment){
            mTotalPointsEarned -= assignment.getPointsEarned();
            mTotalPointsPossible -= assignment.getPointsPossible();

            mCoursePercentage = (mTotalPointsEarned / mTotalPointsPossible) * 100;

            mAssignments.remove(assignment);
        }

        private List<Assignment> getAssignments() {
            return mAssignments;
        }

        private Assignment getAssignment(UUID id) {
            for (Assignment assignment : mAssignments) {
                if (assignment.getAssignmentId().equals(id)) {
                    return assignment;
                }
            }
            return null;
        }

        private void updateAssignmentList(){
            //Reset point totals
            mTotalPointsEarned = 0;
            mTotalPointsPossible = 0;
            //Recalculate point totals
            for (Assignment assignment: mAssignments) {
                mTotalPointsEarned += assignment.getPointsEarned();
                mTotalPointsPossible += assignment.getPointsPossible();
            }
            //Recalculate coursePercentage
            mCoursePercentage = (mTotalPointsEarned / mTotalPointsPossible) * 100;
        }

        private double getCoursePercentage() {
            return mCoursePercentage;
        }
    }
}
