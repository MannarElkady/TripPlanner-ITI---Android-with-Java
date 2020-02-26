package com.example.tripplanner.pasttrips;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.tripplanner.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PastTripsFragment extends Fragment {
    private PastTripsViewModel pastTripsViewModel;
    private PastTripsAdapter pastTripsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private LinearLayout noPastTripsLayout;


    public PastTripsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_trips, container, false);

        noPastTripsLayout = view.findViewById(R.id.no_past_trips_layout);
        recyclerView = view.findViewById(R.id.past_trips_recyclerview);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pastTripsViewModel = new ViewModelProvider(requireActivity()).get(PastTripsViewModel.class);

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


    }
}
