package biz.no_ip.evedschob.classtracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


/**
 * Created by Evan on 11/20/2017.
 */

public class EditCourseFragment extends Fragment {

    private static final String ARG_COURSE_ID = "course_id";

    public static EditCourseFragment newInstance(String courseId){
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

    private CheckBox[] mCheckBoxes = new CheckBox[7];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String courseId = (String) getArguments().getString(ARG_COURSE_ID);

        if (courseId != null){
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

        //Checkboxes
        mCheckBoxes[0] = (CheckBox) view.findViewById(R.id.course_checkbox_monday);
        mCheckBoxes[1] = (CheckBox) view.findViewById(R.id.course_checkbox_tuesday);
        mCheckBoxes[2] = (CheckBox) view.findViewById(R.id.course_checkbox_wednesday);
        mCheckBoxes[3] = (CheckBox) view.findViewById(R.id.course_checkbox_thursday);
        mCheckBoxes[4] = (CheckBox) view.findViewById(R.id.course_checkbox_friday);
        mCheckBoxes[5] = (CheckBox) view.findViewById(R.id.course_checkbox_saturday);
        mCheckBoxes[6] = (CheckBox) view.findViewById(R.id.course_checkbox_sunday);

        //If there is a course being passed in:
        if (mCourse != null){
            //Set the text of the EditTexts to those contained in the view
            mCourseNameField.setText(mCourse.getCourseName());
            mSubjectField.setText(mCourse.getSubject());
            mSectionField.setText(mCourse.getSection());
            mCRNField.setText(mCourse.getCRN());

            for (int i = 0; i < 6; i++) {
                if (mDays[i]){
                    mCheckBoxes[i].setChecked(true);
                }
            }

            mUniversalButton.setText(R.string.edit_course_button_text);
            mUniversalButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        //If there is no course:
        } else {
            //Leave the text views with their default hints
            //Set the text of the universal button to "Create Course"
            mUniversalButton.setText(R.string.create_course_button_text);
            mUniversalButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            //There is no course to delete at this point so hide the delete button.
            mDeleteButton.setVisibility(View.GONE);
        }

        return view;
    }
}
