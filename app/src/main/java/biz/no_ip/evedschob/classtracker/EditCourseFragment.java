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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by Evan on 11/20/2017.
 */

public class EditCourseFragment extends Fragment {

    private static final String ARG_COURSE_ID = "course_id";

    public static EditCourseFragment newInstance(String courseId) {
        Bundle args = new Bundle();

        args.putString(ARG_COURSE_ID, courseId);

        EditCourseFragment fragment = new EditCourseFragment();

        fragment.setArguments(args);

        return fragment;
    }

    private Course mCourse;
    private boolean[] mDays;

    private EditText mCourseNameField;
    private EditText mSubjectField;
    private EditText mSectionField;
    private EditText mCRNField;

    private Button mUniversalButton;
    private Button mDeleteButton;
    private Button mStartTimeButton;
    private Button mEndTimeButton;

    private CheckBox[] mCheckBoxes = new CheckBox[6];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String courseId = (String) getArguments().getString(ARG_COURSE_ID);

        if (courseId != null) {
            mCourse = CourseList.get(getActivity()).getCourse(courseId);
            mDays = mCourse.getDays();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_course_activity, container, false);

        //Get references to the views
        mCourseNameField = (EditText) view.findViewById(R.id.course_name_edit_text);
        mSubjectField = (EditText) view.findViewById(R.id.course_subj_edit_text);
        mSectionField = (EditText) view.findViewById(R.id.course_sect_edit_text);
        mCRNField = (EditText) view.findViewById(R.id.course_id_edit_text);

        //References to buttons
        mUniversalButton = (Button) view.findViewById(R.id.universal_course_button);
        mDeleteButton = (Button) view.findViewById(R.id.course_delete_button);
        mStartTimeButton = (Button) view.findViewById(R.id.start_time_button);
        mEndTimeButton = (Button) view.findViewById(R.id.end_time_button);

        //Checkboxes
        mCheckBoxes[0] = (CheckBox) view.findViewById(R.id.course_checkbox_monday);
        mCheckBoxes[1] = (CheckBox) view.findViewById(R.id.course_checkbox_tuesday);
        mCheckBoxes[2] = (CheckBox) view.findViewById(R.id.course_checkbox_wednesday);
        mCheckBoxes[3] = (CheckBox) view.findViewById(R.id.course_checkbox_thursday);
        mCheckBoxes[4] = (CheckBox) view.findViewById(R.id.course_checkbox_friday);
        mCheckBoxes[5] = (CheckBox) view.findViewById(R.id.course_checkbox_saturday);

        //If there is a course being passed in:
        if (mCourse != null) {
            //Set the text of the EditTexts to those contained in the view
            mCourseNameField.setText(mCourse.getCourseName());
            mSubjectField.setText(mCourse.getSubject());
            mSectionField.setText(mCourse.getSection());
            mCRNField.setText(mCourse.getCRN());

            for (int i = 0; i < 5; i++) {
                if (mDays[i]) {
                    mCheckBoxes[i].setChecked(true);
                }
            }

            mUniversalButton.setText(R.string.edit_course_button_text);
            mUniversalButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Update the course based on the input values
                    try {
                        //Validate the input before updating the course
                        if (validCRN(mCRNField.getText().toString())){
                            mCourse.setCRN(mCRNField.getText().toString());
                            mCourse.setCourseName(mCourseNameField.getText().toString());
                            mCourse.setSubject(mSubjectField.getText().toString());
                            mCourse.setSection(mSectionField.getText().toString());
                            for (int i = 0; i < 5; i++) {
                                if (mCheckBoxes[i].isChecked()) {
                                    mDays[i] = true;
                                } else {
                                    mDays[i] = false;
                                }
                            }
                            EditCourseFragment.this.getActivity().finish();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //Set the OnClickListener for the Delete button
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Create an alert box confirming the delete action
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    String alertString = getString(R.string.delete_confirmation_string, mCourse.getCourseName());
                    builder.setTitle(alertString);
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        //If the user clicks the confirm button
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Tell the course list to remove the course
                            CourseList.get(getActivity()).removeCourse(mCourse);
                            //Close the dialog box.
                            dialogInterface.dismiss();
                            //Close the activity hosting the course that was just deleted
                            EditCourseFragment.this.getActivity().finish();
                        }
                    });
                    builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        //If the user clicks the cancel button
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Do nothing and close dialog
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
            //If there is no course:
        } else {
            //Leave the text views with their default hints
            //Set the text of the time buttons to the default from the strings
            mStartTimeButton.setText(R.string.course_start_time_button_label);
            mEndTimeButton.setText(R.string.course_end_time_button_label);
            //Set the text of the universal button to the default from the strings
            mUniversalButton.setText(R.string.create_course_button_text);
            mUniversalButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Parse the values from the layout controls
                    try {
                        String crn = mCRNField.getText().toString();
                        String sub = mSubjectField.getText().toString();
                        String sec = mSectionField.getText().toString();
                        String name = mCourseNameField.getText().toString();
                        boolean[] days = new boolean[6];
                        for (int i = 0; i < 5; i++) {
                            if (mCheckBoxes[i].isChecked()) {
                                days[i] = true;
                            }
                        }
                        //Ensure that the CRN field is not left blank
                        if (validCRN(crn)) {
                            Course course = new Course(crn, sub, sec, name, days);
                            CourseList.get(getContext()).addCourse(course);
                            EditCourseFragment.this.getActivity().finish();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //There is no course to delete at this point so hide the delete button.
            mDeleteButton.setVisibility(View.GONE);
        }

        return view;
    }

    //Boolean method to validate the CRN field input
    private boolean validCRN(String crn){
        //Check to make sure the CRN/ID field is not empty
        if (mCRNField.length() != 0) {
            //Ensure that there is no duplicate CRN
            if (CourseList.get(getActivity()).getCourse(crn) == null){
                return true;
            } else {
                //If there is a duplicate, Toast an error message
                Toast.makeText(getActivity(),
                        R.string.duplicate_crn_error_string, Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            //If the field is empty, Toast an error message
            Toast.makeText(getActivity(),
                    R.string.empty_crn_error_string, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
