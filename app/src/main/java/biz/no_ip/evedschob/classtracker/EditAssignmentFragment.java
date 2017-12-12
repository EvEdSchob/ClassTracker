package biz.no_ip.evedschob.classtracker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import static biz.no_ip.evedschob.classtracker.CoursePreferencesManager.updatePreferences;

/**
 * Created by Evan on 11/28/2017.
 */

public class EditAssignmentFragment extends Fragment {

    private static final String ARG_COURSE_ID = "course_id";
    private static final String ARG_ASSIGNMENT_ID = "assignment_id";

    public static EditAssignmentFragment newInstance(String courseId, UUID assignmentId){
        Bundle args = new Bundle();

        args.putString(ARG_COURSE_ID, courseId);
        args.putSerializable(ARG_ASSIGNMENT_ID, assignmentId);

        EditAssignmentFragment fragment = new EditAssignmentFragment();

        fragment.setArguments(args);

        return fragment;
    }

    private Course mCourse;
    private Assignment mAssignment;

    private EditText mAssignmentNameField;
    private EditText mPointsEarnedField;
    private EditText mPointsPossibleField;

    private Button mAssignmentButton;
    private Button mDeleteButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String courseId = (String) getArguments().getString(ARG_COURSE_ID);
        UUID assignmentId = (UUID) getArguments().getSerializable(ARG_ASSIGNMENT_ID);

        mCourse = CourseList.get(getActivity()).getCourse(courseId);
        getActivity().setTitle(mCourse.getCourseName());
        if (assignmentId != null) {
            mAssignment = mCourse.getAssignment(assignmentId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_assignment_activity, container,false);

        //Get references to the views
        mAssignmentNameField = (EditText) view.findViewById(R.id.assignment_name_edit_text);
        mPointsEarnedField = (EditText) view.findViewById(R.id.assignment_points_earned_edit_text);
        mPointsPossibleField = (EditText) view.findViewById(R.id.assignment_points_possible_edit_text);

        //References to the buttons
        mAssignmentButton = (Button) view.findViewById(R.id.universal_assignment_button);
        mDeleteButton = (Button) view.findViewById(R.id.assignment_delete_button);

        //If there is an assignment being passed in:
        if (mAssignment != null){
            //Set the text of the views to the values contained in the assignment
            mAssignmentNameField.setText(mAssignment.getAssignmentName());
            mPointsEarnedField.setText(String.valueOf(mAssignment.getPointsEarned()));
            mPointsPossibleField.setText(String.valueOf(mAssignment.getPointsPossible()));

            //Set the text of the universal button to the value
            //of the string in the resources
            mAssignmentButton.setText(R.string.edit_assignment_button_text);
            mAssignmentButton.setOnClickListener(new View.OnClickListener() {
                //When the universal button is clicked
                //Currently the "Edit Assignment" button
                @Override
                public void onClick(View view) {
                    try {
                        //Update the assignment with the EditText values
                        mAssignment.setAssignmentName(mAssignmentNameField.getText().toString());
                        mAssignment.setPointsEarned(Double.parseDouble(
                                mPointsEarnedField.getText().toString()));
                        mAssignment.setPointsPossible(Double.parseDouble(
                                mPointsPossibleField.getText().toString()));
                        //Update the course with the new assignment values
                        mCourse.updateCourse();
                        //Update the SharedPreferences
                        updatePreferences(getContext());
                        //Close the activity
                        EditAssignmentFragment.this.getActivity().finish();
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //When the delete button is pressed do the following:
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Create an alert box confirming the delete action
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    String alertString = getString(R.string.delete_confirmation_string,
                            mAssignment.getAssignmentName());
                    builder.setTitle(alertString);
                    //If the action is confirmed
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Remove the assignment from the course
                            mCourse.removeAssignment(mAssignment);
                            //Update the SharedPreferences
                            updatePreferences(getContext());
                            //Close the dialog box
                            dialogInterface.dismiss();
                            //Close the activity hosting the assignment that was just deleted
                            EditAssignmentFragment.this.getActivity().finish();
                        }
                    });
                    //If the action is rejected
                    builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        //If the cancel option is selected:
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Do nothing and close dialog
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create().show();
                }
            });
        //If there is no assignment:
        } else {
            //Leave the text views with their default hints.
            //Set the text of the universal button to the value of
            //the string in the resources
            mAssignmentButton.setText(R.string.create_assignment_button_text);
            mAssignmentButton.setOnClickListener(new View.OnClickListener() {
                //When the universal button is clicked
                //Currently the "Create Assignment" button
                @Override
                public void onClick(View view) {
                    String title;
                    double pe; //Points Earned
                    double pp; //Points Possible
                    //Parse the values of the EditTexts
                    try {
                        title =  mAssignmentNameField.getText().toString();
                        pe = Double.parseDouble(mPointsEarnedField.getText().toString());
                        pp = Double.parseDouble(mPointsPossibleField.getText().toString());

                        //Create a new assignment based on the input fields
                        Assignment assignment = new Assignment(title, pe, pp);
                        //Add the assignment to the course
                        mCourse.addAssignment(assignment);
                        //Update the SharedPreferences
                        updatePreferences(getContext());
                        //Close the activity
                        EditAssignmentFragment.this.getActivity().finish();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //There is no assignment to delete so hide the delete button
            mDeleteButton.setVisibility(View.GONE);
        }

        return view;
    }
}
