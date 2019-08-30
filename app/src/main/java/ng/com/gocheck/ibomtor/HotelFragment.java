package ng.com.gocheck.ibomtor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HotelFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<Tour> mTourList = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTourList.add(new Tour(R.string.hotel1, R.string.location8, R.string.le_meridien_desc, R.drawable.le_meridian, R.drawable.le_meridian1, R.drawable.le_meridian2));
//        mTourList.add(new Tour(R.string.hotel2, R.string.location1, R.string.monty, R.drawable.yondu,R.drawable.d3));
//        mTourList.add(new Tour(R.string.hotel3, R.string.location8, R.string.Pinnacle, R.drawable.yondu,R.drawable.d3));
//        mTourList.add(new Tour(R.string.hotel4, R.string.location8, R.string.Tevoli, R.drawable.yondu,R.drawable.d3));
//        mTourList.add(new Tour(R.string.hotel5, R.string.location8, R.string.Dasty, R.drawable.yondu,R.drawable.d3));
        mTourList.add(new Tour(R.string.hotel6, R.string.location8, R.string.EEM, R.drawable.eemjm_hotels,R.drawable.eemjm_hotels, R.drawable.eemjm_hotels));
//        mTourList.add(new Tour(R.string.hotel7, R.string.location8, R.string.Sheraton, R.drawable.));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        tourAdapter adapter = new tourAdapter(mTourList);
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    private class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mHeader, mLocation;
        private ImageView mImageView;
        private Tour mTour;

        public viewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mHeader = itemView.findViewById(R.id.header);
            mImageView = itemView.findViewById(R.id.scene);
//            mLocation = itemView.findViewById(R.id.location);
        }

        public void bind(Tour tour){
            mTour = tour;
            mImageView.setImageResource(tour.getImageResourceId());
            mHeader.setText(tour.getName());
//            mLocation.setText(tour.getLocation());
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(getActivity(), "Click successful", Toast.LENGTH_SHORT).show();
            Intent intent = DetailActivity.newIntent(getActivity(), mTour.getName(), mTour.getLocation(),
                    mTour.getDescription(), mTour.getImageResourceId(), mTour.getImageResourceId2(), mTour.getmImageResourceId3());
            startActivity(intent);
        }
    }

    private class tourAdapter extends RecyclerView.Adapter<viewHolder>{
        private List<Tour> mTours;
        public tourAdapter(List<Tour> tours){
            mTours = tours;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_tour, parent, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, int position) {
            Tour tour = mTours.get(position);
            holder.bind(tour);
        }

        @Override
        public int getItemCount() {
            return mTourList.size();
        }
    }
}
