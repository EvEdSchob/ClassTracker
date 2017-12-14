package biz.no_ip.evedschob.classtracker;


import android.content.Intent;
import android.os.Bundle;
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


import java.util.List;

import static biz.no_ip.evedschob.classtracker.CoursePreferencesManager.*;

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
        startUp(getContext());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_activity, container, false);

        mCourseListRecyclerView = (RecyclerView) view.findViewById(R.id.generic_recycler_view);

        mCourseListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
                    mCourse.getTimeAsFormattedString(mCourse.getStartTime()),
                    mCourse.getTimeAsFormattedString(mCourse.getEndTime()));
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
