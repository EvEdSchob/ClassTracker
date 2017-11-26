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

        //Dummy classes to be removed later
        for (int i = 0; i < 5; i++) {
            //Using course because "class" is a reserved word
            Course course = new Course();

            int CRN = 12345 + i;
            course.setCRN(String.valueOf(CRN));

            course.setSubject("CIS");

            int SEC = 101 + (i * 10);
            course.setSection(String.valueOf(SEC));

            course.setCourseName("Mobile App Development");

            Boolean[] days = {true, false, true, false, false, false, false};
            course.setDays(days);

            course.setGrade(3.5);
        }

    }

    public void addClass(Course c) {
        mCourses.add(c);
    }

    public List<Course> getCourses() {
        return mCourses;
    }

    public Course getClass(String CRN){
        for (Course course : mCourses) {
            if (course.getCRN().equals(CRN)){
                return course;
            }
        }
        return null;
    }
}
