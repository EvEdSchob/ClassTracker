package biz.no_ip.evedschob.classtracker;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Evan on 11/20/2017.
 */

public class CourseListFragment extends Fragment {

    private RecyclerView mCourseListRecyclerView;

    private ClassAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = mPreferences.edit();

        //Use the shared mPreferences to see if this block of code has been run before.
        //If not, import the dummy data from the JSON file.
        //Open a shared preferences editor, and set the "firstTime" value to true.
        //This will prevent the code from running on all future sessions of this app.
        if (!mPreferences.getBoolean("firstTime", false)) {
            importJSON();
            editor.putBoolean("firstTime", true).apply();
        } else {
            String json = mPreferences.getString("jsonData", null);
            if (json != null){
                //Create a Type using the TypeToken to define a java object with an
                //array/list as its root object
                Type founderListType = new TypeToken<ArrayList<Course>>(){}.getType();
                //Use GSON, with the JSON string and the type from the previous
                //step to recreate the CourseList as a new List object.
                List<Course> courses = new Gson().fromJson(json, founderListType);
                //Pass that newly created list back to the CourseList
                //Replacing the original dummy data
                CourseList.get(getActivity()).setCourses(courses);
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_activity, container, false);

        mCourseListRecyclerView = (RecyclerView) view.findViewById(R.id.generic_recycler_view);

        mCourseListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        //**************************
        //Testing
        //**************************

//        //Use the dummy data contained in the CourseList to create a JSON string
//        String json = new Gson().toJson(CourseList.get(getActivity()).getCourses());
//
//        //Create a Type using the TypeToken to define a java object with an
//        //array/list as its root object
//        Type founderListType = new TypeToken<ArrayList<Course>>(){}.getType();
//        //Use GSON, with the JSON string and the type from the previous
//        //step to recreate the CourseList as a new List object.
//        List<Course> courses = new Gson().fromJson(json, founderListType);
//        //Pass that newly created list back to the CourseList
//        //Replacing the original dummy data
//        CourseList.get(getActivity()).setCourses(courses);


        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        updatePreferences();
    }

    private void updatePreferences() {
        Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();
        String json = gson.toJson(CourseList.get(getActivity()).getCourses());
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("jsonData", json).apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePreferences();
        updateUI();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.course_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = EditCourseActivity.newIntent(getActivity(), null);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {

        CourseList courseList = CourseList.get(getActivity());

        List<Course> courses = courseList.getCourses();

        if (mAdapter == null) {
            mAdapter = new ClassAdapter(courses);

            mCourseListRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }


    //Method using a scanner to read in a JSON file from the raw resources
    private void importJSON() {
        Scanner courseListScanner = new Scanner(getContext()
                .getResources().openRawResource(R.raw.course_list));

        //If the first line of the course list file is not empty
        if (courseListScanner.hasNextLine()) {
            //Get the first line of the course list file
            String line = courseListScanner.nextLine();
            if (!line.isEmpty()) {
                //Create a Type using the TypeToken to define a java object with an
                //array/list as its root object
                Type founderListType = new TypeToken<ArrayList<Course>>() {
                }.getType();
                //Use GSON, with the JSON string and the type from the previous
                //step to recreate the CourseList as a new List object.
                List<Course> courses = new Gson().fromJson(line, founderListType);
                //Pass that newly created list back to the CourseList
                //Replacing the original dummy data
                CourseList.get(getActivity()).setCourses(courses);
            }
        }
        courseListScanner.close();
    }


    //*****************************
    //Inner classes
    //*****************************

    private class CourseHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        private Course mCourse;

        //Course Details TextViews
        private TextView mClassSubjTextView;
        private TextView mClassSecTextView;
        private TextView mClassNameTextView;

        //Course Schedule TextViews
        private TextView mClassDaysTextView;
        private TextView mClassTimeTextView;

        //Grade TextView
        private TextView mClassGradeTextView;

        public CourseHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            //Wire up the class detail widgets
            mClassSubjTextView = (TextView)
                    itemView.findViewById(R.id.list_item_class_subj_text_view);
            mClassSecTextView = (TextView)
                    itemView.findViewById(R.id.list_item_class_sec_text_view);
            mClassNameTextView = (TextView)
                    itemView.findViewById(R.id.list_item_class_name_text_view);

            //Wire up the class schedule widgets
            mClassDaysTextView = (TextView)
                    itemView.findViewById(R.id.list_item_class_days_text_view);
            mClassTimeTextView = (TextView)
                    itemView.findViewById(R.id.list_item_class_time_text_view);

            //Wire up the class grade widget
            mClassGradeTextView = (TextView)
                    itemView.findViewById(R.id.list_item_class_grade_text_view);
        }

        public void bindCourse(Course course) {
            mCourse = course;
            mClassSubjTextView.setText(mCourse.getSubject());
            mClassSecTextView.setText(mCourse.getSection());
            mClassNameTextView.setText(mCourse.getCourseName());

            mClassDaysTextView.setText(mCourse.getDaysAsString());

            String TimeString = getString(R.string.class_time_format,
                    mCourse.getStartTimeAsString(), mCourse.getEndTimeAsString());
            mClassTimeTextView.setText(TimeString);

            mClassGradeTextView.setText(String.valueOf(mCourse.getGrade()));
        }

        @Override
        public void onClick(View view) {
            //On standard click, open the CourseActivity
            Intent intent = CourseActivity.newIntent(getActivity(), mCourse.getCRN());
            startActivity(intent);
        }

        @Override
        public boolean onLongClick(View view) {
            //On long click, open the EditAssignmentActivity
            Intent intent = EditCourseActivity.newIntent(getActivity(), mCourse.getCRN());
            startActivity(intent);
            return true;
        }
    }

    private class ClassAdapter extends RecyclerView.Adapter<CourseHolder> {
        private List<Course> mCourses;

        public ClassAdapter(List<Course> courses) {
            mCourses = courses;
        }

        @Override
        public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_class, parent, false);
            return new CourseHolder(view);
        }

        @Override
        public void onBindViewHolder(CourseHolder holder, int position) {
            Course course = mCourses.get(position);
            holder.bindCourse(course);
        }

        @Override
        public int getItemCount() {
            return mCourses.size();
        }
    }
}
