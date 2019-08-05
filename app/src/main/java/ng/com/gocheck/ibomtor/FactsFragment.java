package ng.com.gocheck.ibomtor;

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

public class FactsFragment extends Fragment {
    private List<Facts> mFactsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Adapter adapter = new Adapter(mFactsList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFactsList = new ArrayList<>();

        mFactsList.add(new Facts(R.string.fact1));
        mFactsList.add(new Facts(R.string.facts2));
        mFactsList.add(new Facts(R.string.facts3));
        mFactsList.add(new Facts(R.string.facts4));
        mFactsList.add(new Facts(R.string.facts5));
        mFactsList.add(new Facts(R.string.facts6));
        mFactsList.add(new Facts(R.string.facts7));
        mFactsList.add(new Facts(R.string.facts8));
        mFactsList.add(new Facts(R.string.facts9));
        mFactsList.add(new Facts(R.string.facts10));
        mFactsList.add(new Facts(R.string.facts11));
    }

    private class viewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.factsText);

        }
    }


    private class Adapter extends RecyclerView.Adapter<viewHolder>{
        private List<Facts> mFacts;
        public Adapter(List<Facts> facts){
            mFacts = facts;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.facts_list, viewGroup, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
            Facts facts = mFacts.get(i);
            viewHolder.textView.setText(facts.getPoints());
        }

        @Override
        public int getItemCount() {
            return mFacts.size();
        }
    }
}
