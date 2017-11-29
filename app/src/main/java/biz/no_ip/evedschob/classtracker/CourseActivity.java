package biz.no_ip.evedschob.classtracker;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by Evan on 11/28/2017.
 */

public class CourseActivity extends SingleFragmentActivity {
    private static final String EXTRA_COURSE_ID =
            "biz.no_ip.evedschob.classtracker.course_ic";

    public static Intent newIntent(Context packageContext, String CRN){
        Intent intent = new Intent(packageContext, CourseActivity.class);
        intent.putExtra(EXTRA_COURSE_ID, CRN);
        return intent;
    }


    @Override
    protected Fragment createFragment() {
        String CRN = (String) getIntent().getStringExtra(EXTRA_COURSE_ID);
        return CourseFragment.newInstance(CRN);}
}
