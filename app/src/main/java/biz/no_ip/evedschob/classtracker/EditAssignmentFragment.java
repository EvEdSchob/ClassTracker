package biz.no_ip.evedschob.classtracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by Evan on 11/28/2017.
 */

public class EditAssignmentFragment extends Fragment {

    private static final String ARG_COURSE_ID = "course_id";
    private static final String ARG_ASSIGNMENT_ID = "assignment_id";

    public static EditAssignmentFragment newInstance(UUID assignmentId){
        Bundle args = new Bundle();

        args.putSerializable(ARG_ASSIGNMENT_ID, assignmentId);

        EditAssignmentFragment fragment = new EditAssignmentFragment();

        fragment.setArguments(args);

        return fragment;
    }

    private Assignment mAssignment;

    private EditText mAssignmentNameField;
    private EditText mPointsEarnedField;
    private EditText mPointsPossibleField;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID assignmentId = (UUID) getArguments().getSerializable(ARG_ASSIGNMENT_ID);


    }
}
