package biz.no_ip.evedschob.classtracker;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Evan on 12/10/2017.
 */

public class CoursePreferencesManager {

    private static SharedPreferences mPreferences;

    private static String courseListJSON;

    private static Gson mGson = new GsonBuilder().serializeSpecialFloatingPointValues().create();


    private CoursePreferencesManager() {

    }

    public static void startUp(Context context){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        //Use the shared mPreferences to see if this block of code has been run before.
        if (mPreferences.getBoolean("firstTime", true)) {
            //If not, import the dummy data from the JSON file.
            importJSON(context);
            //Use the shared preferences editor to set the "firstTime" value to false.
            //This will prevent the code from running on all future sessions of this app.
            mPreferences.edit().putBoolean("firstTime", false).apply();
            //If this is not the first launch
        } else {
            //Get the JSON string from the SharedPreferences
            //If for some reason there is no JSON data in the shared preferences
            //return an empty array. This will start the app with an empty CourseList
            courseListJSON = mPreferences.getString("jsonData", "[]");
            restorePreferences(courseListJSON, context);
        }
    }

    public static void updatePreferences(Context context) {
        courseListJSON = mGson.toJson(CourseList.get(context).getCourses());
        mPreferences.edit().putString("jsonData", courseListJSON).apply();
    }

    private static void restorePreferences(String inputString, Context context) {
        Type courseListType = new TypeToken<ArrayList<Course>>() {}.getType();
        List<Course> courses = mGson.fromJson(inputString, courseListType);
        CourseList.get(context).setCourses(courses);
    }

    //Use the course list JSON contained within the package to
    //fill the CourseList
    private static void importJSON(Context context) {
        Scanner courseListScanner = new Scanner(
                context.getResources().openRawResource(R.raw.course_list));
        //If the first line of the course list file is not empty
        if (courseListScanner.hasNextLine()) {
            //Get the first line of the course list file
            String line = courseListScanner.nextLine();
            if (!line.isEmpty()) {
                restorePreferences(line, context);
            }
        }
        courseListScanner.close();
    }

}

