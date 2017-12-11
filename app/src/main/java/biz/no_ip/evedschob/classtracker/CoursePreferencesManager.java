package biz.no_ip.evedschob.classtracker;


import android.content.Context;
import android.content.SharedPreferences;
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
            //Use the restore preferences method to put the stored
            //JSON string back into the CourseList
            restorePreferences(courseListJSON, context);
        }
    }

    public static void updatePreferences(Context context) {
        //Serialize the course list and put it in the JSON string
        courseListJSON = mGson.toJson(CourseList.get(context).getCourses());
        //Save the JSON string to the SharedPreferences
        mPreferences.edit().putString("jsonData", courseListJSON).apply();
    }

    //Using an input string, deserialize a CourseList
    //object and set it to the course list.
    private static void restorePreferences(String inputString, Context context) {
        //Create a Type identifier for the CourseList object. Its root
        //element is an array/list that cannot be cleanly parsed to JSON
        //without first identifying the type of objects in the list.
        Type courseListType = new TypeToken<ArrayList<Course>>() {}.getType();
        //Using the input string from the method and the type identifier
        //that was just created deserialize the JSON string into an object
        List<Course> courses = mGson.fromJson(inputString, courseListType);
        //Take the newly de-serialized List object and use it to set the
        //app's course list.
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

