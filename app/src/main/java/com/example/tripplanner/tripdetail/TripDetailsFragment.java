package com.example.tripplanner.tripdetail;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripplanner.R;
import com.example.tripplanner.core.constant.TripStatus;
import com.example.tripplanner.core.model.Note;
import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.floatingicon.FloatingIconService;
import com.example.tripplanner.homescreen.homeview.CurrentTripsHomeFragmentDirections;
import com.example.tripplanner.reminder.AlarmReciever;
import com.example.tripplanner.reminder.ReminderViewModel;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class TripDetailsFragment extends Fragment {

    /*Manar*/
    private TripDetailsViewModel mViewModel;
    private Trip selectedTrip;
    TextView tripNameText;
    TextView tripDateText;
    TextView tripStartLocationText;
    TextView tripEndLocationText;
    Button startTripButton;
    Button showDirectionButton;
    TextView tripStatus;
    public static final int ALARM_REQUEST_CODE = 101;
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;

    public static TripDetailsFragment newInstance() {
        return new TripDetailsFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(TripDetailsViewModel.class);
        View view= inflater.inflate(R.layout.trip_details_fragment, container, false);
        tripNameText = view.findViewById(R.id.tripNameDetailsTextField);
        getActivity().findViewById(R.id.buttom_nav).setVisibility(View.INVISIBLE);
        tripDateText = view.findViewById(R.id.dateTimeTripDetailsTextView);
        tripStartLocationText = view.findViewById(R.id.startLocationDetailsTextField);
        tripEndLocationText = view.findViewById(R.id.endLocationDetailsTextField);
        startTripButton = view.findViewById(R.id.startTripButton);
        tripStatus = view.findViewById(R.id.statusDetailsTextField);
        showDirectionButton = view.findViewById(R.id.showDirectionButton);
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
        tripDateText.setText("Date : "+selectedTrip.getTripDate()+ " Time: "+selectedTrip.getTripTime());
        tripStartLocationText.setText(selectedTrip.getStartLocation());
        tripEndLocationText.setText(selectedTrip.getEndLocation());
        tripStatus.setText(selectedTrip.getTripStatus());
        showDirectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f",selectedTrip.getStartLatitude(),
                        selectedTrip.getStartLongitude(),selectedTrip.getEndtLatitude(),selectedTrip.getEndLongitude());
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
        startTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String uri = String.format(Locale.ENGLISH, "google.navigation:q=%f,%f", selectedTrip.getEndtLatitude(), selectedTrip.getEndLongitude());
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    Trip tripToUpdate = new Trip(selectedTrip.getTitle(), selectedTrip.getTripDate(), selectedTrip.getTripTime(), selectedTrip.getStartLocation(), selectedTrip.getEndLocation()
                            , selectedTrip.getStartLatitude(), selectedTrip.getStartLongitude(), selectedTrip.getEndtLatitude(), selectedTrip.getEndLongitude()
                            , selectedTrip.getListOfNotes(), TripStatus.CANCELED);
                    mViewModel.updateTrip(selectedTrip, tripToUpdate);

                    if(!checkSettings())
                        displayFloatingIcon();

                    cancelAlarm();
                    startActivity(intent);

            }
        });
    }
    /*Manar*/
    /*Reham*/
    public boolean checkSettings(){
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getActivity()));
    }
    //cancel alarm
    private void cancelAlarm() {
        alarmMgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlarmReciever.class);
        alarmIntent = PendingIntent.getBroadcast(getActivity(), ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.cancel(alarmIntent);
        Toast.makeText(getActivity().getApplicationContext(), "Alarm Cancelled", Toast.LENGTH_LONG).show();
    }

    private void displayFloatingIcon(){
        ArrayList<String> notes = new ArrayList<>();
        if(selectedTrip.getListOfNotes() != null)
            for(Note note : selectedTrip.getListOfNotes())
                notes.add(note.getDescription());

        Intent intent = new Intent(getActivity(), FloatingIconService.class);
        intent.putStringArrayListExtra("notes", notes);
        getActivity().startService(intent);
    }
    /*Reham*/
}
