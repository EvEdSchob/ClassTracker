package biz.no_ip.evedschob.classtracker;

import java.sql.Time;

/**
 * Created by Evan on 11/20/2017.
 */

public class Course {

    private String mCRN;
    private String mSubject;
    private String mSection;
    private String mCourseName;
    private boolean[] mDays;
    private Time mStartTime;
    private Time mEndTime;

    private double mGrade;

    private AssignmentList mAssignmentList = new AssignmentList();

    public Course(String crn, String sub, String sec, String name, boolean[] days){
        mCRN = crn;
        mSubject = sub;
        mSection = sec;
        mCourseName = name;
        mDays = days;

        //Dummy Assignments to be removed later
        for (int j = 0; j < 16; j++) {
            String aName = "Assignment #" + String.valueOf(j);
            Assignment assignment = new Assignment(aName, 85 + j, 100);
            addAssignment(assignment);
        }
        //End of dummy data

        mGrade = calculateGrade(mAssignmentList.getCoursePercentage());
    }

    public void addAssignment(Assignment assignment){
        mAssignmentList.addAssignment(assignment);
        calculateGrade(mAssignmentList.getCoursePercentage());
    }

    private double calculateGrade(double percent){
        if(percent >= 90){
            mGrade = 4.0;
        } else if (percent >= 85){
            mGrade = 3.5;
        } else if (percent >= 80){
            mGrade = 3.0;
        } else if (percent >= 75){
            mGrade = 2.5;
        } else if (percent >= 70){
            mGrade = 2.0;
        } else if (percent >= 65){
            mGrade = 1.5;
        } else if (percent >= 60){
            mGrade = 1.0;
        } else {
            mGrade = 0.0;
        }

        return mGrade;
    }


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

    public String getDaysAsString() {
        String outputString = "";

        if(mDays[0]){outputString += "M, ";}
        if(mDays[1]){outputString += "T, ";}
        if(mDays[2]){outputString += "W, ";}
        if(mDays[3]){outputString += "R, ";}
        if(mDays[4]){outputString += "F, ";}
        if(mDays[5]){outputString += "S, ";}
        if(mDays[6]){outputString += "U, ";}

        return outputString;
    }


    public Time getStartTime() {
        return mStartTime;
    }

    public String getStartTimeAsString() {
        
        //To be replaced with proper time to string conversion
        //at a later time // TODO: 11/26/2017
        return "6:00";
    }

    public void setStartTime(Time startTime) {
        mStartTime = startTime;
    }

    public Time getEndTime() {
        return mEndTime;
    }

    public String getEndTimeAsString(){

        //To be replaced with proper time to string conversion
        //at a later time
        return "8:30";
    }

    public void setEndTime(Time endTime) {
        mEndTime = endTime;
    }


    public double getGrade() {
        return mGrade;
    }

    public AssignmentList getAssignmentList() {
        return mAssignmentList;
    }
}
