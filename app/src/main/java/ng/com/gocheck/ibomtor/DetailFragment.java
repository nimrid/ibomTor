package ng.com.gocheck.ibomtor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class DetailFragment extends Fragment {
    private TextView mName, mDescription, mLocation;
    private ViewFlipper viewFlipper;
    private int name, location, desc;
    private int image1, image2, image3;
    int [] mImages = new int[3];


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        name = getActivity().getIntent().getIntExtra(DetailActivity.EXTRA_NAME, 0);
        location = getActivity().getIntent().getIntExtra(DetailActivity.EXTRA_LOCATION, 1);
        desc = getActivity().getIntent().getIntExtra(DetailActivity.EXTRA_DESCRIPTION, 2);
        image1 = getActivity().getIntent().getIntExtra(DetailActivity.EXTRA_IMAGE, 3);
        image2 = getActivity().getIntent().getIntExtra(DetailActivity.EXTRA_IMG, 4);
        image3 = getActivity().getIntent().getIntExtra(DetailActivity.EXTRA_IMG2, 5);

        mImages[0]= image1;
        mImages[1] = image2;
        mImages[2] = image3;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_tour, container, false);

        mName = view.findViewById(R.id.actual_name);
        mName.setText(name);

        mLocation = view.findViewById(R.id.actual_location);
        mLocation.setText(location);

        mDescription = view.findViewById(R.id.description);
        mDescription.setText(desc);

        ImageView map = view.findViewById(R.id.mapButton);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                Toast.makeText(getActivity(), "Turn locator on", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        mLocation.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MapsActivity.class);
            Toast.makeText(getActivity(), "Turn locator on", Toast.LENGTH_LONG).show();
            startActivity(intent);
        });

        viewFlipper = view.findViewById(R.id.imageScroll);
        for (int i = 0; i < mImages.length; i++){
            Flipper(mImages[i]);
        }

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.locate_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.locate_icon:
                Intent intent = new Intent(getContext(), MapsActivity.class);
                Toast.makeText(getActivity(), "Turn locator on", Toast.LENGTH_LONG).show();
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void Flipper(int image){
        ImageView view = new ImageView(getContext());
        view.setImageResource(image);
        viewFlipper.addView(view);
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
        viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
    }
}
