package biz.no_ip.evedschob.classtracker;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by Evan on 12/4/2017.
 */

public class EditCourseActivity extends SingleFragmentActivity {
    private static final String EXTRA_COURSE_ID =
            "biz.no_ip.evedschob.classtracker.course_id";

    public static Intent newIntent(Context packageContext, String crn){
        Intent intent = new Intent(packageContext, EditCourseActivity.class);
        intent.putExtra(EXTRA_COURSE_ID, crn);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String crn = (String) getIntent().getStringExtra(EXTRA_COURSE_ID);
        return EditCourseFragment.newInstance(crn);}
}
