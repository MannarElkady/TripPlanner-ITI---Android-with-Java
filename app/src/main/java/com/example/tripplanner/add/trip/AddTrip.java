package com.example.tripplanner.add.trip;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tripplanner.R;
import com.example.tripplanner.core.model.Note;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class AddTrip extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private static final int AUTOCOMPLETE_TO_PLACE_REQUEST_ID =1;
    private static final int AUTOCOMPLETE_FROM_PLACE_REQUEST_ID =2;
    private static final String TAG = "AddTrip";
    private static final String API_KEY = "AIzaSyALcG0V7aW1ezNKKc_74PTXTKyAG7hdM5w";
    private TextView timeTextView , dateTextView , doneTextView, toTextView,fromTextView;
    private Button timeBtn,dateBtn,toBtn,fromBtn;
    private EditText title;
    private ImageView addNote;
    private List<Note> notes ;
    private RecyclerView noteRecyclerView;
    private NoteAdapter noteAdapter;
    private List<Place.Field> fields = Arrays.asList(Place.Field.LAT_LNG,Place.Field.ADDRESS, Place.Field.NAME);
    String pattern = "hh:mm:ss a";
    DateFormat dateFormat = new SimpleDateFormat(pattern);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notes = new ArrayList<>();
        noteAdapter = new NoteAdapter(getActivity().getApplicationContext(),notes);
        initPlaces();
    }

    private void initPlaces() {

        Places.initialize(getActivity().getApplicationContext(), API_KEY);

        PlacesClient placesClient = Places.createClient(getActivity().getApplicationContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_trip, container, false);
        timeBtn = view.findViewById(R.id.timeButton);
        dateBtn = view.findViewById(R.id.dateButton);
        timeTextView = view.findViewById(R.id.timeTextView);
        dateTextView = view.findViewById(R.id.dateTextView);

        toBtn = view.findViewById(R.id.toButton);
        fromBtn = view.findViewById(R.id.fromButton);
        toTextView = view.findViewById(R.id.toTextView);
        fromTextView = view.findViewById(R.id.fromTextView);
        title = view.findViewById(R.id.titleEidtText);
        doneTextView = view.findViewById(R.id.doneTextView);

        addNote = view.findViewById(R.id.addNote);

        toBtn.setOnClickListener((v)->{
            Log.i(TAG, "Place: to ");
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fields)
                    .build(getActivity().getApplicationContext());
            startActivityForResult(intent,AUTOCOMPLETE_TO_PLACE_REQUEST_ID);
        });

        fromBtn.setOnClickListener((v)->{
            Log.i(TAG, "Place: from ");
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fields)
                    .build(getActivity().getApplicationContext());
            startActivityForResult(intent,AUTOCOMPLETE_FROM_PLACE_REQUEST_ID);
        });


        timeBtn.setOnClickListener((v)->{
            Calendar now = Calendar.getInstance();
            TimePickerDialog time = TimePickerDialog.newInstance(this,now.get(Calendar.HOUR),now.get(Calendar.MINUTE),false);
            time.show(getParentFragmentManager(),"TimePicker");
        });
        dateBtn.setOnClickListener(v->{
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog date = DatePickerDialog.newInstance(this,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            date.show(getParentFragmentManager(),"DatePicker");
        });

        doneTextView.setOnClickListener(v->{
        });

        addNote.setOnClickListener((v)->{

        });



        return view;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        dateTextView.setText(year+"/"+monthOfYear+"/"+dayOfMonth);
        //TODO:add date to trip object
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        hourOfDay = (hourOfDay==0)?12:hourOfDay;
        StringBuilder sb = new StringBuilder();
        if(minute<10)
            sb.append("0").append(minute).toString();
        else
            sb.append(minute);

        timeTextView.setText(hourOfDay+":"+sb.toString());
        //TODO: add to trip object
    }

    //Recive result from place search fragment
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == AUTOCOMPLETE_TO_PLACE_REQUEST_ID){
            if(resultCode == Activity.RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                String toName =place.getName();
                if( toName!=null){
                    toTextView.setText(toName);
                }
                Log.i(TAG, "Place: Address " + place.getAddress() + ", " + place.getLatLng());
            }else{
                Log.i(TAG, "Place: Error ");

            }
        }else if (requestCode == AUTOCOMPLETE_FROM_PLACE_REQUEST_ID){
            if(resultCode == Activity.RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                String fromName =place.getName();
                if( fromName!=null){
                    fromTextView.setText(fromName);
                }
                Log.i(TAG, "Place: Address " + place.getAddress() + ", " + place.getLatLng());
            }else{
                Log.i(TAG, "Place: Error ");

            }
        }

    }


}
