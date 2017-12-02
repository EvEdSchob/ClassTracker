package biz.no_ip.evedschob.classtracker;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Evan on 11/28/2017.
 */

public class EditAssignmentActivity extends SingleFragmentActivity {
    private static final String EXTRA_ASSIGNMENT_ID =
            "bibiz.no_ip.evedschob.classtracker.assignment_id";


    public static Intent newIntent(Context packageContext, UUID uuid){
        Intent intent = new Intent(packageContext, EditAssignmentActivity.class);
        intent.putExtra(EXTRA_ASSIGNMENT_ID, uuid);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID uuid = (UUID) getIntent().getSerializableExtra(EXTRA_ASSIGNMENT_ID);

        return EditAssignmentFragment.newInstance(uuid);
    }
}
