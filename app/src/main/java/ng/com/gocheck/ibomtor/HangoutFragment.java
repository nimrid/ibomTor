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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HangoutFragment extends Fragment {
    private List<Tour> mTourList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTourList.add(new Tour(R.string.hangout2, R.string.location8, R.string.tropicana, R.drawable.ibom_tropicana, R.drawable.ibom_tropicana1, R.drawable.ibom_tropicana2));
        mTourList.add(new Tour(R.string.hangout3, R.string.location8, R.string.plaza, R.drawable.ibom_plaza, R.drawable.ibom_plaza1, R.drawable.ibom_plaza2));
        mTourList.add(new Tour(R.string.hangout4, R.string.location8, R.string.vista, R.drawable.vista_restaurant, R.drawable.vista_restaurant1, R.drawable.vista_restaurant2));
        mTourList.add(new Tour(R.string.hangout5, R.string.location8, R.string.cinema, R.drawable.silverbird_cinema, R.drawable.silverbird_cinema1, R.drawable.silverbird_cinema2));
//        mTourList.add(new Tour(R.string.hangout6, R.string.location8, R.string.playground, R.drawable.pl));
        mTourList.add(new Tour(R.string.hangout7, R.string.location8, R.string.discovery_park, R.drawable.discovery_park,R.drawable.discovery_park1, R.drawable.discovery_park2));
//        mTourList.add(new Tour(R.string.hangout8, R.string.location8, R.string.silver_lounge, R.drawable.silverbird_cinema_uyo,R.drawable.silverbird_cinema_uyo1));
        mTourList.add(new Tour(R.string.hangout9, R.string.location8, R.string.unity, R.drawable.unity_park, R.drawable.unity_park1, R.drawable.unity2));
        mTourList.add(new Tour(R.string.hangout1, R.string.location2, R.string.raffia_city, R.drawable.raffia_city_plaza1, R.drawable.raffia_city_plaza1, R.drawable.raffia_city_plaza1));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_tour,container, false);
        RecyclerView recyclerView = view.findViewById(R.id.tour_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Adapter adapter = new Adapter(mTourList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mHeader, mLocation;
        private Tour mTour;

        public myViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mHeader = itemView.findViewById(R.id.header);
            mLocation = itemView.findViewById(R.id.location);
        }

        public void bind(Tour tour){
            mTour = tour;
            mHeader.setText(tour.getName());
            mLocation.setText(tour.getLocation());
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
