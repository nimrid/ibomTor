package ng.com.gocheck.ibomtor;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.os.Bundle;

public class FactsActivity extends SingleFragmentActivity {
    private ActionBar mActionBar;

    @Override
    protected Fragment createFragment() {
        mActionBar = getSupportActionBar();
        return new FactsFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact);
    }
}
