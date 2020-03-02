package com.example.tripplanner.pasttrips;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.tripplanner.R;
import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.homescreen.homeview.CurrentTripsHomeFragment;

import java.util.List;

import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PastTripsFragment extends Fragment {

    private PastTripsViewModel pastTripsViewModel;
    private PastTripsAdapter pastTripsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private LinearLayout noPastTripsLayout;
    private Button viewMapBtn;
    private final String TAG = "PastTripsFragment";
    private CurrentTripsHomeFragment.OnRecycleItemClickListener pastTripListner;
    private List<Trip> pastTrips;

    public PastTripsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_past_trips, container, false);
        getActivity().findViewById(R.id.buttom_nav).setVisibility(VISIBLE);
        noPastTripsLayout = view.findViewById(R.id.no_past_trips_layout);
        recyclerView = view.findViewById(R.id.past_trips_recyclerview);
        viewMapBtn = view.findViewById(R.id.view_map_floating_btn);

        viewMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.fragments_functionality_layout).navigate(PastTripsFragmentDirections.actionPastTripsFragmentToMapFragment2());
            }
        });

        pastTripListner = new CurrentTripsHomeFragment.OnRecycleItemClickListener() {
            @Override
            public void onItemClick(View item, int position) {
                Navigation.findNavController(getActivity(), R.id.fragments_functionality_layout).navigate(PastTripsFragmentDirections.actionPastTripsFragmentToTripDetailsFragment(pastTrips.get(position)));
            }
        };
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, "onActivityCreated");

        pastTripsViewModel = new ViewModelProvider(requireActivity()).get(PastTripsViewModel.class);

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onStart() {
        super.onStart();

        pastTripsViewModel.getPastTrips().observe(getViewLifecycleOwner(), (trips) -> {
            Log.i(TAG, "***********pastTripsViewModel.getPastTrips()");
            pastTrips = trips;
            if(trips.size() > 0)
                displayPastTrips();
            else
                displayNoPastTrips();
        });

    }

    public void displayPastTrips(){
        pastTripsAdapter = new PastTripsAdapter(pastTrips, pastTripListner);
        recyclerView.setAdapter(pastTripsAdapter);

        recyclerView.setVisibility(View.VISIBLE);
        noPastTripsLayout.setVisibility(View.INVISIBLE);
    }

    public void displayNoPastTrips(){
        recyclerView.setVisibility(View.INVISIBLE);
        noPastTripsLayout.setVisibility(View.VISIBLE);
    }

}
