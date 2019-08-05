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

public class AttractionFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<Tour> mTourList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTourList.add(new Tour(R.string.attration1, R.string.location3, R.string.ibeno, R.drawable.ibeno1, R.drawable.ibeno_beach, R.drawable.iben02));
        mTourList.add(new Tour(R.string.attraction2, R.string.location8, R.string.Stadium, R.drawable.akwa_stadium2, R.drawable.akwa_stadium, R.drawable.akwa_stadium1));
        mTourList.add(new Tour(R.string.attraction3, R.string.location8, R.string.e_library, R.drawable.e_library, R.drawable.e_library1, R.drawable.e_library2));
        mTourList.add(new Tour(R.string.attraction4, R.string.location8, R.string.specialist_hospital, R.drawable.specialist_hospital, R.drawable.specialist_hospital1, R.drawable.specialist_hospital3));
        mTourList.add(new Tour(R.string.attraction5, R.string.location8, R.string.Bridge_no_return, R.drawable.bridge_of_no_return, R.drawable.bridge_of_no_return1, R.drawable.bridge_of_no_return2));
        mTourList.add(new Tour(R.string.attraction6, R.string.location4, R.string.amalgamation, R.drawable.amalgamation_house, R.drawable.amalgamation_house1, R.drawable.amalgamation_house2));
        mTourList.add(new Tour(R.string.attraction7, R.string.location8, R.string.presbyterian, R.drawable.presybterian_church1, R.drawable.presybterian_church, R.drawable.presybterian_church2));
        mTourList.add(new Tour(R.string.attraction8, R.string.location6, R.string.mbo_forest, R.drawable.mbo, R.drawable.mbo1, R.drawable.mbo));
        mTourList.add(new Tour(R.string.atrraction14, R.string.location5, R.string.airport, R.drawable.airport, R.drawable.airport1, R.drawable.airport3));
        mTourList.add(new Tour(R.string.attraction10, R.string.location4, R.string.slessor_house, R.drawable.mary_slessors_tomb, R.drawable.mary_slessors_tomb1, R.drawable.mary_slessors_tomb));
//        mTourList.add(new Tour(R.string.attraction11, R.string.location4, R.string.royal_niger_boat, R.drawable.yondu,R.drawable.d3));
        mTourList.add(new Tour(R.string.attraction12, R.string.location9, R.string.Sea_port, R.drawable.ibom_deep_sea, R.drawable.ibom_deep_seaport, R.drawable.ibom_deep_seaport2));
        mTourList.add(new Tour(R.string.attraction13, R.string.location8, R.string.worship, R.drawable.worship_centre, R.drawable.worship_centre1, R.drawable.worship_centre2));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Adapter adapter = new Adapter(mTourList);
        mRecyclerView.setAdapter(adapter);
        return view;
    }


    private class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mHeader;
        private ImageView mImageView;
        private Tour mTour;

        public myViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mImageView = itemView.findViewById(R.id.scene);
            mHeader = itemView.findViewById(R.id.header);
        }

        public void bind(Tour tour){
            mTour = tour;
            mImageView.setImageResource(tour.getImageResourceId());
            mHeader.setText(tour.getName());
        }

        @Override
        public void onClick(View v) {
            Intent intent = DetailActivity.newIntent(getActivity(), mTour.getName(), mTour.getLocation(),
                    mTour.getDescription(), mTour.getImageResourceId(), mTour.getImageResourceId2(), mTour.getmImageResourceId3());
            startActivity(intent);
        }
    }

    private class Adapter extends RecyclerView.Adapter<myViewHolder>{
        private List<Tour> mTours;
        public Adapter(List<Tour> tours){
            mTours = tours;
        }

        @NonNull
        @Override
        public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_tour, parent, false);
            return new myViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
            Tour tour = mTours.get(position);
            holder.bind(tour);
        }

        @Override
        public int getItemCount() {
            return mTours.size();
        }
    }
}
