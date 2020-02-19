package com.example.tripplanner.homescreen.homeview;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripplanner.R;
import com.example.tripplanner.core.model.Trip;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AllTripsFragment extends Fragment {

    private AllTripsViewModel mViewModel;
    private AllTripsHomeAdapter tripsHomeAdapter;
    private LinearLayoutManager layoutManager;

    public static AllTripsFragment newInstance() {
        return new AllTripsFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Trip> list = new ArrayList<>();
        list.add(new Trip("title","date","start","last"));
        list.add(new Trip("title","date","start","last"));
        list.add(new Trip("title","date","start","last"));
        list.add(new Trip("title","date","start","last"));
        list.add(new Trip("title","date","start","last"));
        list.add(new Trip("title","date","start","last"));
        list.add(new Trip("title","date","start","last"));
        list.add(new Trip("title","date","start","last"));
        list.add(new Trip("title","date","start","last"));
        list.add(new Trip("title","date","start","last"));
        list.add(new Trip("title","date","start","last"));
        tripsHomeAdapter = new AllTripsHomeAdapter(getActivity(),list);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.all__trips_fragment, container, false);
        RecyclerView rv = v.findViewById(R.id.trips_recyclerview);
        rv.setHasFixedSize(true);
        v.findViewById(R.id.no_trips_layout).setVisibility(View.INVISIBLE);
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(tripsHomeAdapter);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AllTripsViewModel.class);
        // TODO: Use the ViewModel
    }

}
