package biz.no_ip.evedschob.classtracker;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;


/**
 * Created by Evan on 12/11/2017.
 */

public class TimePickerFragment extends DialogFragment{

    public static final String EXTRA_TIME =
            "biz.no_ip.evedschob.classtracker.return_time";

    private static final String ARG_TIME = "DialogTime";

    private static final String REQUEST_CODE = "RequestCode";

    //Start Time, New Course
    private static final int REQUEST_STNC = 1;
    //End Time, New Course
    private static final int REQUEST_ETNC = 2;
    //Start Time, Old Course
    private static final int REQUEST_STOC = 3;
    //End Time, Old Course
    private static final int REQUEST_ETOC = 4;

    private TimePicker mTimePicker;
    private View mView;
    private String mTime;

    public static TimePickerFragment newInstance(int request){
        Bundle args = new Bundle();

        args.putInt(REQUEST_CODE, request);

        TimePickerFragment fragment = new TimePickerFragment();

        fragment.setArguments(args);

        return fragment;
    }

    public static TimePickerFragment newInstance(int request, String time){
        Bundle args = new Bundle();

        args.putInt(REQUEST_CODE, request);
        args.putString(ARG_TIME, time);

        TimePickerFragment fragment = new TimePickerFragment();

        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int request = getArguments().getInt(REQUEST_CODE);
        String timePickerTitle = "";
        switch (request) {
            case REQUEST_STNC:
                mView = initializeView();
                timePickerTitle = getString(R.string.start_time_time_picker_title);
                break;
            case REQUEST_ETNC:
                mView = initializeView();
                timePickerTitle = getString(R.string.end_time_time_picker_title);

                break;
            case REQUEST_STOC:
                mView = initializeView();
                handleTimeArgs();
                timePickerTitle = getString(R.string.start_time_time_picker_title);
                break;
            case REQUEST_ETOC:
                mView = initializeView();
                handleTimeArgs();
                timePickerTitle = getString(R.string.end_time_time_picker_title);
                break;
            default:
                Toast.makeText(getActivity(), "Invalid Request", Toast.LENGTH_SHORT).show();
                TimePickerFragment.this.getActivity().finish();
        }

        return new AlertDialog.Builder(getActivity())
                .setView(mView)
                .setTitle(timePickerTitle)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mTime = mTimePicker.getCurrentHour() + ":" + mTimePicker.getCurrentMinute();
                        sendResult(Activity.RESULT_OK, mTime);
                    }
                })
                .create();
    }

    private View initializeView() {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);
        mTimePicker = (TimePicker) view.findViewById(R.id.dialog_time_time_picker);
        return view;
    }

    private void handleTimeArgs() {
        mTime = getArguments().getString(ARG_TIME);
        String[] hourMinute;
        hourMinute = mTime.split(":");
        mTimePicker.setCurrentHour(Integer.valueOf(hourMinute[0]));
        mTimePicker.setCurrentMinute(Integer.valueOf(hourMinute[1]));
    }

    private void sendResult(int resultCode, String time){
        if(getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, time);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
