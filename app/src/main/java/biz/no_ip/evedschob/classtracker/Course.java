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
    private Boolean[] mDays;
    private Time mStartTime;
    private Time mEndTime;

    private double mGrade;


    public String getCRN() {
        return mCRN;
    }

    public void setCRN(String CRN) {
        mCRN = CRN;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public String getSection() {
        return mSection;
    }

    public void setSection(String section) {
        mSection = section;
    }

    public String getCourseName() {
        return mCourseName;
    }

    public void setCourseName(String courseName) {
        mCourseName = courseName;
    }

    public Boolean[] getDays() {
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

    public void setDays(Boolean[] days) {
        mDays = days;
    }

    public Time getStartTime() {
        return mStartTime;
    }

    public String getStartTimeAsString() {

        //To be replaced with proper time to string conversion
        //at a later time
        return "2:35";
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
        return "4:35";
    }

    public void setEndTime(Time endTime) {
        mEndTime = endTime;
    }


    public double getGrade() {
        return mGrade;
    }

    public void setGrade(double grade) {
        mGrade = grade;
    }
}
