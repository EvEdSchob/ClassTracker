package biz.no_ip.evedschob.classtracker;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DialogTitle;
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
import java.util.UUID;

/**
 * Created by Evan on 11/20/2017.
 */

public class CourseFragment extends Fragment {
    //String variable to hold the Course ID From the bundle.
    private static final String ARG_COURSE_ID = "course_id";

    public static CourseFragment newInstance(String CRN) {
        Bundle args = new Bundle();

        args.putString(ARG_COURSE_ID, CRN);

        CourseFragment fragment = new CourseFragment();

        fragment.setArguments(args);

        return fragment;
    }

    private Course mCourse;

    private RecyclerView mAssignmentListRecyclerView;

    private AssignmentAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String CRN = (String) getArguments().getString(ARG_COURSE_ID);
        setHasOptionsMenu(true);

        mCourse = CourseList.get(getActivity()).getCourse(CRN);
        getActivity().setTitle(mCourse.getCourseName());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_activity, container, false);

        mAssignmentListRecyclerView = (RecyclerView) view.findViewById(R.id.generic_recycler_view);

        mAssignmentListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
        inflater.inflate(R.menu.assignment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = EditAssignmentActivity.newIntent(getActivity(),
                mCourse.getCRN(), null);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {
        List<Assignment> assignments = mCourse.getAssignmentList();

        if (mAdapter == null) {
            mAdapter = new AssignmentAdapter(assignments);

            mAssignmentListRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

    }

    //*****************************
    //Inner classes
    //*****************************

    private class AssignmentHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Assignment mAssignment;

        private TextView mAssignmentNameTextView;
        private TextView mAssignmentPercentTextView;
        private TextView mAssignmentScoreTextView;

        public AssignmentHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            //Wire up the assignment's TextViews
            mAssignmentNameTextView = (TextView)
                    itemView.findViewById(R.id.list_item_assignment_name_text_view);
            mAssignmentPercentTextView = (TextView)
                    itemView.findViewById(R.id.list_item_assignment_percent_text_view);
            mAssignmentScoreTextView = (TextView)
                    itemView.findViewById(R.id.list_item_assignment_score_text_view);
        }

        public void bindAssignment(Assignment assignment) {
            mAssignment = assignment;
            mAssignmentNameTextView.setText(mAssignment.getAssignmentName());

            //Retrieve the percentage from the assignment, format it using the format
            //string the output it to the TextView on the list item.
            String percentString = getString(R.string.assignment_percent_format_text
                    , mAssignment.getPercentage());
            mAssignmentPercentTextView.setText(percentString);

            //A similar process to above but with two inputs to the format string.
            String scoreString = getString(R.string.assignment_score_label_format_text,
                    mAssignment.getPointsEarned(),
                    mAssignment.getPointsPossible());
            mAssignmentScoreTextView.setText(scoreString);
        }

        @Override
        public void onClick(View view) {
            //Do nothing yet
            //Will eventually open editable assignment details.
            Intent intent = EditAssignmentActivity.newIntent(getActivity(),
                    mCourse.getCRN(), mAssignment.getAssignmentId());
            startActivity(intent);
        }
    }



    private class AssignmentAdapter extends RecyclerView.Adapter<AssignmentHolder> {
        private List<Assignment> mAssignmentList;

        public AssignmentAdapter(List<Assignment> assignmentList) {
            mAssignmentList = assignmentList;
        }

        @Override
        public AssignmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_assignment, parent, false);
            return new AssignmentHolder(view);
        }

        @Override
        public void onBindViewHolder(AssignmentHolder holder, int position) {
            Assignment assignment = mAssignmentList.get(position);

            holder.bindAssignment(assignment);
        }

        @Override
        public int getItemCount() {
            return mAssignmentList.size();
        }
    }
}
