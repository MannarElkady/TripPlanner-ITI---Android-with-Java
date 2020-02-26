package com.example.tripplanner.tripdetail;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tripplanner.R;
import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.homescreen.homeview.CurrentTripsHomeFragmentDirections;

public class TripDetailsFragment extends Fragment {

    private TripDetailsViewModel mViewModel;
    private Trip selectedTrip;
    TextView tripNameText;
    TextView tripDateText;
    TextView tripStartLocationText;
    TextView tripEndLocationText;

    public static TripDetailsFragment newInstance() {
        return new TripDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.trip_details_fragment, container, false);
        tripNameText = view.findViewById(R.id.tripNameDetailsTextField);
        tripDateText = view.findViewById(R.id.dateTimeTripDetailsTextView);
        tripStartLocationText = view.findViewById(R.id.startLocationDetailsTextField);
        tripEndLocationText = view.findViewById(R.id.endLocationDetailsTextField);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TripDetailsViewModel.class);
        // TODO: Use the ViewModel
        TripDetailsFragmentArgs tripDetailsFragmentArgs = TripDetailsFragmentArgs.fromBundle(getArguments());
        selectedTrip = tripDetailsFragmentArgs.getTripData();
        tripNameText.setText(selectedTrip.getTitle());
        tripDateText.setText(selectedTrip.getTripDate());
        tripStartLocationText.setText(selectedTrip.getStartLocation());
        tripEndLocationText.setText(selectedTrip.getEndLocation());

    }

}
