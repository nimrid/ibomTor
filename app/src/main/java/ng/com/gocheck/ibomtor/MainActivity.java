package ng.com.gocheck.ibomtor;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActionBar = getSupportActionBar();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListerner);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new AttractionFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListerner = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.attract_nav:
                            selectedFragment = new AttractionFragment();
                            mActionBar.setTitle(R.string.attraction_center);
                            break;
                        case R.id.hangout_nav:
                            selectedFragment = new HangoutFragment();
                            mActionBar.setTitle(R.string.hangout);
                            break;
                        case R.id.hotel_nav :
                            selectedFragment = new HotelFragment();
                            mActionBar.setTitle(R.string.hotels);
                            break;
                        case R.id.museum_nav:
                            selectedFragment = new MuseumFragment();
                            mActionBar.setTitle(R.string.museum);
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }

            };
}
