package com.example.tripplanner.homescreen.homeview;

import androidx.lifecycle.Observer;
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

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class AllTripsFragment extends Fragment {

    private AllTripsViewModel mViewModel;
    private AllTripsHomeAdapter tripsHomeAdapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView rv;

    public static AllTripsFragment newInstance() {
        return new AllTripsFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* List<Trip> list = new ArrayList<>();
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
        list.add(new Trip("title","date","start","last"));*/
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.all__trips_fragment, container, false);
        rv = v.findViewById(R.id.trips_recyclerview);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AllTripsViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getAllTrips().observe(getActivity(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                if(trips != null){
                    if(trips.size()!=0)
                        displayTrips(trips);
                    else
                        displayNoTrips();
                }
                else
                    displayNoTrips();
            }
        });
    }
    public void displayNoTrips() {
        getActivity().findViewById(R.id.no_trips_layout).setVisibility(VISIBLE);
        getActivity().findViewById(R.id.trips_recyclerview).setVisibility(INVISIBLE);
    }
    public void displayTrips(List<Trip> trips){
        getActivity().findViewById(R.id.no_trips_layout).setVisibility(INVISIBLE);
        getActivity().findViewById(R.id.trips_recyclerview).setVisibility(VISIBLE);
        tripsHomeAdapter = new AllTripsHomeAdapter(getActivity(),trips);
        rv.setAdapter(tripsHomeAdapter);
    }
}
