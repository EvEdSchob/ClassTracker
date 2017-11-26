package biz.no_ip.evedschob.classtracker;

import android.support.v4.app.Fragment;

public class ClassListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ClassListFragment();
    }
}
