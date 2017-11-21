package biz.no_ip.evedschob.classtracker;

import java.sql.Time;

/**
 * Created by Evan on 11/20/2017.
 */

public class Class {

    private String mCRN;
    private String mSubject;
    private String mSection;
    private String mClassName;
    private Boolean[] mDays;
    private Time mStartTime;
    private Time mEndTime;


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

    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String className) {
        mClassName = className;
    }

    public Boolean[] getDays() {
        return mDays;
    }

    public void setDays(Boolean[] days) {
        mDays = days;
    }

    public Time getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Time startTime) {
        mStartTime = startTime;
    }

    public Time getEndTime() {
        return mEndTime;
    }

    public void setEndTime(Time endTime) {
        mEndTime = endTime;
    }
}
