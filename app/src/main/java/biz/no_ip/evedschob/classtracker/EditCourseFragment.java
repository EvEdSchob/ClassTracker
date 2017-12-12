package biz.no_ip.evedschob.classtracker;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import static biz.no_ip.evedschob.classtracker.CoursePreferencesManager.updatePreferences;


/**
 * Created by Evan on 11/20/2017.
 */

public class EditCourseFragment extends Fragment {

    private static final String ARG_COURSE_ID = "course_id";

    private static final String DIALOG_TIME = "DialogTime";

    //Request codes for the TimePicker
    //Start Time, New Course
    private static final int REQUEST_STNC = 1;
    //End Time, New Course
    private static final int REQUEST_ETNC = 2;
    //Start Time, Old Course
    private static final int REQUEST_STOC = 3;
    //End Time, Old Course
    private static final int REQUEST_ETOC = 4;

    public static EditCourseFragment newInstance(String courseId) {
        Bundle args = new Bundle();

        args.putString(ARG_COURSE_ID, courseId);

        EditCourseFragment fragment = new EditCourseFragment();

        fragment.setArguments(args);

        return fragment;
    }

    private Course mCourse;
    private boolean[] mDays;
    private String mStartTime;
    private String mEndTime;

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
            //Call the method for updating/deleting the course
            courseViewEdit();

            //If there is no course:
        } else {
            //Call the method for creating a new course
            courseViewCreate();
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case 1:
            case 3:
                mStartTime = data.getStringExtra(TimePickerFragment.EXTRA_TIME);
                mStartTimeButton.setText(mStartTime);
                break;
            case 2:
            case 4:
                mEndTime = data.getStringExtra(TimePickerFragment.EXTRA_TIME);
                mEndTimeButton.setText(mEndTime);
                break;
            default:
                Toast.makeText(getActivity(), "Invalid Result Request", Toast.LENGTH_SHORT).show();
        }
    }

    private void courseViewCreate() {
        //Leave the text views with their default hints
        //Set the text of the time buttons to the default from the strings
        mStartTimeButton.setText(R.string.course_start_time_button_label);
        mEndTimeButton.setText(R.string.course_end_time_button_label);

        mStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                //Request code "1" for Start Time, New Class
                TimePickerFragment dialog = TimePickerFragment.newInstance(REQUEST_STNC);
                dialog.setTargetFragment(EditCourseFragment.this, REQUEST_STNC);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        mEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                //Request code "2" for End Time, New Class
                TimePickerFragment dialog = TimePickerFragment.newInstance(REQUEST_ETNC);
                dialog.setTargetFragment(EditCourseFragment.this, REQUEST_ETNC);
                dialog.show(manager, DIALOG_TIME);
            }
        });
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
                    if (validInputCRN(crn)) {
                        //Create a new course
                        Course course = new Course(crn, sub, sec, name, days);
                        //Add it to the CourseList
                        CourseList.get(getContext()).addCourse(course);
                        //Update the SharedPreferences
                        updatePreferences(getContext());
                        //Exit the activity
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

    private void courseViewEdit() {
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

        mStartTimeButton.setText(mCourse.getStartTimeAsString());
        mEndTimeButton.setText(mCourse.getEndTimeAsString());
        mStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                //Request code "3" for Start Time, Old Class
                TimePickerFragment dialog = TimePickerFragment
                        .newInstance(REQUEST_STOC, mCourse.getStartTimeAsString());
                dialog.setTargetFragment(EditCourseFragment.this, REQUEST_STOC);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        mEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                //Request code "4" for End Time, Old Class
                TimePickerFragment dialog = TimePickerFragment
                        .newInstance(REQUEST_ETOC, mCourse.getEndTimeAsString());
                dialog.setTargetFragment(EditCourseFragment.this, REQUEST_ETOC);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        mUniversalButton.setText(R.string.edit_course_button_text);
        mUniversalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Update the course based on the input values
                try {
                    //Validate the input before updating the course
                    if (validInputCRN(mCRNField.getText().toString())) {
                        //Pull the values from the views and update the course
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
                        mCourse.setDays(mDays);
                        //Update the preferences
                        updatePreferences(getContext());
                        //Close the fragment and return to the course list
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
                //If the action is confirmed:
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    //If the user clicks the confirm button
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Tell the course list to remove the course
                        CourseList.get(getActivity()).removeCourse(mCourse);
                        //Update the SharedPreferences
                        updatePreferences(getContext());
                        //Close the dialog box.
                        dialogInterface.dismiss();
                        //Close the activity hosting the course that was just deleted
                        EditCourseFragment.this.getActivity().finish();
                    }
                });
                //If the action is rejected
                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    //If the user clicks the cancel button
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Do nothing and close dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }


    //Boolean method to validate the CRN field input
    private boolean validInputCRN(String crn) {
        //Check to make sure the CRN/ID field is not empty
        if (crn.length() != 0) {
            //See if there is an existing course with that CRN
            if (CourseList.get(getActivity()).getCourse(crn) != null) {
                //If there is a duplicate, see if it is the currently open course
                if (crn.equals(mCourse.getCRN())) {
                    return true;
                } else {
                    //If the duplicate is not the current course throw an error.
                    Toast.makeText(getActivity(),
                            R.string.duplicate_crn_error_string, Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                //If there is no course with the input CRN return true
                return true;
            }
        } else {
            //If the field is empty, Toast an error message
            Toast.makeText(getActivity(),
                    R.string.empty_crn_error_string, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
