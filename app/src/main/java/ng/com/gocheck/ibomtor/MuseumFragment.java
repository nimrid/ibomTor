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

public class MuseumFragment extends Fragment {
    private List<Tour> mTourList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTourList.add(new Tour(R.string.Museum, R.string.location8, R.string.Tara, R.drawable.house_of_tara, R.drawable.house_of_tara1, R.drawable.house_of_tara2));
        mTourList.add(new Tour(R.string.museum1, R.string.location4, R.string.luggard_house, R.drawable.lord_luggard, R.drawable.lord_luggard1, R.drawable.lord_luggard2));
//        mTourList.add(new Tour(R.string.museum2, R.string.location8, R.string.ibiobio_museum, R.drawable.i));
        mTourList.add(new Tour(R.string.museum3, R.string.location4, R.string.colonial_museum, R.drawable.national_museum_of_colonial_history, R.drawable.national_museum_of_colonial_history1, R.drawable.national_museum_of_colonial_history2));
        mTourList.add(new Tour(R.string.museum4, R.string.location5, R.string.oron_museum, R.drawable.oron_musuem, R.drawable.oron_musuem1, R.drawable.oron_musuem2));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
