package biz.no_ip.evedschob.classtracker;

import java.util.UUID;

/**
 * Created by Evan on 11/21/2017.
 */

public class Assignment {

    private UUID mAssignmentId;
    private String mAssignmentName;
    private double mPointsEarned;
    private double mPointsPossible;
    private double mPercentage;

    public Assignment(String assignmentName, double pointsEarned, double pointsPossible) {
        mAssignmentId = UUID.randomUUID();
        mAssignmentName = assignmentName;
        mPointsEarned = pointsEarned;
        mPointsPossible = pointsPossible;

        mPercentage = CalcPercent(pointsEarned, pointsPossible);
    }

    private double CalcPercent(double earned, double possible){
        double percentage = (earned/ possible)*100;
        return percentage;
    }

    public UUID getAssignmentId() {
        return mAssignmentId;
    }

    public String getAssignmentName() {
        return mAssignmentName;
    }

    public double getPointsEarned() {
        return mPointsEarned;
    }

    public double getPointsPossible() {
        return mPointsPossible;
    }

    public double getPercentage() {
        return mPercentage;
    }

    public void setAssignmentName(String assignmentName) {
        mAssignmentName = assignmentName;
    }

    public void setPointsEarned(double pointsEarned) {
        mPointsEarned = pointsEarned;
        mPercentage = CalcPercent(mPointsEarned, mPointsPossible);
    }

    public void setPointsPossible(double pointsPossible) {
        mPointsPossible = pointsPossible;
        mPercentage = CalcPercent(mPointsEarned, mPointsPossible);
    }
}
