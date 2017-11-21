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

    public Assignment(String assignmentName, int pointsEarned, int pointsPossible) {
        mAssignmentId = UUID.randomUUID();
        mAssignmentName = assignmentName;
        mPointsEarned = pointsEarned;
        mPointsPossible = pointsPossible;

        mPercentage = CalcPercent(pointsEarned, pointsPossible);
    }

    private double CalcPercent(double earned, double possible){
        double percentage = earned/ possible;
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

}
