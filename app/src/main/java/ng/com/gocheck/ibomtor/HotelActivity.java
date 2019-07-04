package ng.com.gocheck.ibomtor;

import android.support.v4.app.Fragment;

public class HotelActivity extends SingleFragmentActivity {
    private android.support.v7.app.ActionBar mActionBar;

    @Override
    protected Fragment createFragment() {
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("Attraction Center");
        return new HotelFragment();
    }
}
