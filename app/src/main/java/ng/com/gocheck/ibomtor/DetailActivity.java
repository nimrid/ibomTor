package ng.com.gocheck.ibomtor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    public final static String EXTRA_NAME = "ng.com.gocheck.ibomtour.name";
    public final static String EXTRA_LOCATION = "ng.com.gocheck.ibomtour.location";
    public final static String EXTRA_DESCRIPTION = "ng.com.gocheck.ibomtour.description";
    public final static String EXTRA_IMAGE = "ng.com.gocheck.ibomtour.image";
    public final static String EXTRA_IMG = "ng.com.gocheck.ibomtour.image1";
    public final static String EXTRA_IMG2 = "ng.com.gocheck.ibomtour.image2";

    public static Intent newIntent(Context context, int name, int location, int desc, int image, int image2, int image3){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_LOCATION, location);
        intent.putExtra(EXTRA_DESCRIPTION, desc);
        intent.putExtra(EXTRA_IMAGE, image);
        intent.putExtra(EXTRA_IMG, image2);
        intent.putExtra(EXTRA_IMG2, image3);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.detail_container);

        if (fragment == null){
            fragment = new DetailFragment();
            fm.beginTransaction().add(R.id.detail_container, fragment).commit();
        }


    }
}
