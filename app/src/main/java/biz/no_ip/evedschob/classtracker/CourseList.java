package biz.no_ip.evedschob.classtracker;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan on 11/21/2017.
 */

public class CourseList {

    private static CourseList sCourseList;

    private List<Course> mCourses;

    public static CourseList get(Context context) {
        if (sCourseList == null) {
            sCourseList = new CourseList(context);
        }
        return sCourseList;
    }

    private CourseList(Context context) {
        mCourses = new ArrayList<>();

        //Dummy courses to be removed later
//        for (int i = 0; i < 5; i++) {
//            int CRN = 12345 + i;
//            String SUBJ = "MATH";
//            int SEC = 101 + (i * 10);
//            int Num = i + 1;
//            String courseName = "Calculus " + String.valueOf(Num);
//            boolean[] days = {false, true, false, true, false, false};
//
//            Course course = new Course(String.valueOf(CRN), SUBJ,
//                    String.valueOf(SEC), courseName, days);
//            addCourse(course);
//        }
        //End of Dummy data
    }

    public void addCourse(Course c) {
        mCourses.add(c);
    }

    public void removeCourse (Course c){
        mCourses.remove(c);
    }

    public List<Course> getCourses() {
        return mCourses;
    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;
    }

    public Course getCourse(String CRN){
        for (Course course : mCourses) {
            if (course.getCRN().equals(CRN)){
                return course;
            }
        }
        return null;
    }
}
