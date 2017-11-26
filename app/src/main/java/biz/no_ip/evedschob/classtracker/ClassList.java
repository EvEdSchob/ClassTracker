package biz.no_ip.evedschob.classtracker;

import android.content.Context;
import android.os.SystemClock;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan on 11/21/2017.
 */

public class ClassList {

    private static ClassList sClassList;

    private List<Class> mClasses;

    public static ClassList get(Context context) {
        if (sClassList == null) {
            sClassList = new ClassList(context);
        }
        return sClassList;
    }

    private ClassList(Context context) {
        mClasses = new ArrayList<>();

        //Dummy classes to be removed later
        for (int i = 0; i < 5; i++) {
            //Using course because "class" is a reserved word
            Class course = new Class();

            int CRN = 12345 + i;
            course.setCRN(String.valueOf(CRN));

            course.setSubject("CIS");

            int SEC = 101 + (i * 10);
            course.setSection(String.valueOf(SEC));

            course.setClassName("Mobile App Development");

            Boolean[] days = {true, false, true, false, false, false, false};
            course.setDays(days);

            course.setGrade(3.5);
        }

    }

    public void addClass(Class c) {
        mClasses.add(c);
    }

    public List<Class> getClasses() {
        return mClasses;
    }

    public Class getClass(String CRN){
        for (Class course : mClasses) {
            if (course.getCRN().equals(CRN)){
                return course;
            }
        }
        return null;
    }
}
