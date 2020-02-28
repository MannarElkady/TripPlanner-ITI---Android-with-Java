package com.example.tripplanner.homescreen.homeview;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tripplanner.R;
import com.example.tripplanner.core.model.Trip;

import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class CurrentTripsHomeFragment extends Fragment {

    private TripsHomeViewModel mViewModel;
    private CurrentTripsHomeAdapter currentTripsHomeAdapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView rv;
    LinearLayout noTrips;
    RecyclerView recyclerView;

    public static CurrentTripsHomeFragment newInstance() {
        return new CurrentTripsHomeFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.home_trips_fragment, container, false);
        rv = v.findViewById(R.id.trips_recyclerview);
        v.findViewById(R.id.no_trips_layout).setVisibility(INVISIBLE);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        noTrips =  getActivity().findViewById(R.id.no_trips_layout);
        recyclerView = getActivity().findViewById(R.id.trips_recyclerview);
        mViewModel = ViewModelProviders.of(this).get(TripsHomeViewModel.class);
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
        getActivity().findViewById(R.id.add_Trip_floating_point).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(CurrentTripsHomeFragmentDirections.actionCurrentTripsHomeFragmentToTripAddition());
            }
        });
    }

    public interface OnRecycleItemClickListener {
        void onItemClick(View item,int position);
    }

    public void displayNoTrips() {
        noTrips.setVisibility(VISIBLE);
        recyclerView.setVisibility(INVISIBLE);
    }
    public void displayTrips(List<Trip> trips){
        noTrips.setVisibility(INVISIBLE);
        recyclerView.setVisibility(VISIBLE);
        //create adapter with it's listener
        currentTripsHomeAdapter = new CurrentTripsHomeAdapter(getActivity(), trips, new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(View item, int position) {
                Toast.makeText(getContext(), "Item Clicked with position: "+ position, Toast.LENGTH_LONG).show();
                Navigation.findNavController(item).navigate(CurrentTripsHomeFragmentDirections.actionCurrentTripsHomeFragmentToTripDetailsFragment(trips.get(position)));
            }
        });
        rv.setAdapter(currentTripsHomeAdapter);
    }
}
