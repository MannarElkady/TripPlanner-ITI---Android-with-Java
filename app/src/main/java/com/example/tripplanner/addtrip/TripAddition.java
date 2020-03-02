package com.example.tripplanner.addtrip;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripplanner.R;
import com.example.tripplanner.core.model.MyDate;
import com.example.tripplanner.core.model.MyDirectionData;
import com.example.tripplanner.core.model.Note;
import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.core.model.User;
import com.example.tripplanner.core.repository.remote.FirestoreRepository;
import com.example.tripplanner.reminder.AlarmReciever;
import com.example.tripplanner.tripdetail.TripAdditionArgs;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TripAddition extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    /*Ashraf*/
    private static final int AUTOCOMPLETE_TO_PLACE_REQUEST_ID = 1;
    private static final int AUTOCOMPLETE_FROM_PLACE_REQUEST_ID = 2;
    private static final String TAG = "AddTrip";
    private static final String API_KEY = "AIzaSyALcG0V7aW1ezNKKc_74PTXTKyAG7hdM5w";
    private static final String toPlaceHolder = "Where to go ?";
    private static final String fromPlaceHolder = "Where to start ?";
    private static final String timePlaceHolder = "The time ?";
    private static final String datePlaceHolder = "The date ?";
    private int selectedDate, selectedTime;
    private TextInputLayout title, to, from, time, date;
    private Button doneButton;
    private ImageView addNote;
    private List<Note> notes;
    private List<Place.Field> fields = Arrays.asList(Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.NAME);
    private double startLat, startLon, endLat, endLon;
    private boolean isTimeSet;
    private boolean isDateSet;
    private FirestoreRepository firestoreRepository;
    private ChipGroup chipGroup;
    private Chip noteChip;
    private LayoutInflater inflater;
    /*Ashraf*/

    /*Manar*/
    private MyDate myDate;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    public static final int ALARM_REQUEST_CODE = 101;
    MyDirectionData myDirectionData = new MyDirectionData();
    Trip newTrip;
    /*Manar*/

    /*Ashraf*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notes = new ArrayList<>();
        inflater = LayoutInflater.from(getContext());
        initPlaces();
    }

    private void initPlaces() {

        Places.initialize(getActivity().getApplicationContext(), API_KEY);

        PlacesClient placesClient = Places.createClient(getActivity().getApplicationContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firestoreRepository = new FirestoreRepository(new User(FirebaseAuth.getInstance().getCurrentUser().getUid()));
        TripAdditionArgs args = TripAdditionArgs.fromBundle(getArguments());
        Trip trip = args.getTripData();
        Log.i("args", "tripId: in activityCreated "+trip.getTripId());

        if(trip.getTitle()!=null){
            updateUIwithPassedTrip(trip);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_trip, container, false);
        title = view.findViewById(R.id.titleId);
        to = view.findViewById(R.id.toId);
        from = view.findViewById(R.id.fromId);
        time = view.findViewById(R.id.timeId);
        date = view.findViewById(R.id.dateId);

        /*Manar*/
        getActivity().findViewById(R.id.buttom_nav).setVisibility(View.GONE);
        /*Manar*/
        myDate = new MyDate();
        doneButton = view.findViewById(R.id.doneId);
        chipGroup = view.findViewById(R.id.chipGroupId);
        noteChip = view.findViewById(R.id.noteChipId);

        addNote = view.findViewById(R.id.addNoteId);

        doneButton.setOnClickListener(v -> {
            if (checkForRequierdData()) {
                Log.i("check", "checkData : true");
                String tripTitle = title.getEditText().getText().toString().trim();
                String tripStartLocation = to.getEditText().getText().toString().trim();
                String tripEndLocation = from.getEditText().getText().toString().trim();
                String tripTime = time.getEditText().getText().toString().trim();
                String tripDate = date.getEditText().getText().toString().trim();

                addTripToFirestore(tripTitle, tripStartLocation, tripEndLocation, tripTime, tripDate, startLat, startLon, endLat, endLon);
                //TODO: 2- get data and initialize an Trip object
                /*Manar*/
                //TODO: 3- add reminder according to time and date selected
                startAlarm(getEquivlentCalender(myDate));
                //TODO: 4- add to firestore and room (if requierd)

                //Navigate to Home Screen
                Navigation.findNavController(getActivity(), R.id.fragments_functionality_layout).navigate(TripAdditionDirections.actionTripAdditionToCurrentTripsHomeFragment());
            } else {
                Toast.makeText(getContext(), "Review Trip Data", Toast.LENGTH_SHORT).show();
                //Navigation.findNavController(getActivity(),R.id.fragments_functionality_layout).navigate(TripAdditionDirections.actionTripAdditionToCurrentTripsHomeFragment());
                /*Manar*/
            }
        });

        to.getEditText().setOnClickListener(v -> {
            to.setError(null);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .build(getActivity().getApplicationContext());
            startActivityForResult(intent, AUTOCOMPLETE_TO_PLACE_REQUEST_ID);
        });
        to.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(getActivity().getApplicationContext());
                startActivityForResult(intent, AUTOCOMPLETE_TO_PLACE_REQUEST_ID);
            }
        });


        from.getEditText().setOnClickListener(v -> {
            from.setError(null);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .build(getActivity().getApplicationContext());
            startActivityForResult(intent, AUTOCOMPLETE_FROM_PLACE_REQUEST_ID);
        });

        from.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(getActivity().getApplicationContext());
                startActivityForResult(intent, AUTOCOMPLETE_FROM_PLACE_REQUEST_ID);
            }
        });

        time.setOnClickListener(timeView -> {
            time.getEditText().setOnClickListener(v -> {
                time.setError(null);
            });
            Calendar now = Calendar.getInstance();
            TimePickerDialog time = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR), now.get(Calendar.MINUTE), true);
            time.show(getParentFragmentManager(), "TimePicker");
        });
        time.setEndIconOnClickListener(timeView -> {
            time.getEditText().setOnClickListener(v -> {
                time.setError(null);
            });
            Calendar now = Calendar.getInstance();
            TimePickerDialog time = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR), now.get(Calendar.MINUTE), true);
            time.show(getParentFragmentManager(), "TimePicker");
        });

        date.setOnClickListener(dateView -> {
            date.getEditText().setOnClickListener(v -> {
                date.setError(null);
            });
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog date = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            date.show(getParentFragmentManager(), "DatePicker");
        });
        date.setEndIconOnClickListener(dateView -> {
            date.getEditText().setOnClickListener(v -> {
                date.setError(null);
            });
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog date = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            date.show(getParentFragmentManager(), "DatePicker");
        });


        addNote.setOnClickListener((v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Title");

            final EditText input = new EditText(getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String noteDesc = input.getText().toString().trim();
                    if (!noteDesc.isEmpty()) {
                        Chip chip = (Chip) inflater.inflate(R.layout.note_item, null, false);
                        chip.setOnCloseIconClickListener(v -> {
                            chipGroup.removeView(v);
                        });
                        chip.setText(noteDesc);
                        chipGroup.addView(chip);
                    }
                }
            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setView(input);
            builder.show();
        });


        return view;
    }

    private void addTripToFirestore(String tripTitle, String tripStartLocation, String tripEndLocation, String tripTime
            , String tripDate, double startLat, double startLon, double endLat, double endLon) {
        newTrip = new Trip(tripTitle, tripDate,tripTime,tripStartLocation, tripEndLocation, startLat, startLon, endLat, endLon);
        if (chipGroup.getChildCount() > 0) {
            notes.clear();
            for (int i = 0; i < chipGroup.getChildCount(); i++) {
                Chip chip = (Chip) chipGroup.getChildAt(i);
                Note note = new Note(chip.getText().toString().trim());
                notes.add(note);
            }
            Log.i(TAG, "array size: " + notes.size());
            newTrip.setListOfNotes(notes);
        }
        firestoreRepository.addTrip(newTrip).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Trip Added Successfully", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean checkForRequierdData() {
        if (title.getEditText().getText().toString().isEmpty()) {
            title.setError("Title is required");
            return false;
        } else {
            title.setErrorEnabled(!title.isErrorEnabled());
        }
        if (to.getEditText().getText().toString().isEmpty()) {
            to.setError("need destination");
            return false;
        } else {
            to.setError(null);
        }
        if (startLat == 0.0 || startLon == 0.0) {
            to.setError("destination not recognized");
            return false;
        } else {
            to.setError(null);
        }

        if (from.getEditText().getText().toString().isEmpty()) {
            from.setError("need start Location");
            return false;
        } else {
            from.setError(null);
        }
        if (endLat == 0.0 || endLon == 0.0) {
            from.setError("start Location not recognized");
            return false;
        } else {
            from.setError(null);
        }
        if (!isTimeSet) {
            time.setError("need start time");
            return false;
        } else {
            time.setError(null);
        }
        if (!isDateSet) {
            date.setError("need date");
            return false;
        } else {
            date.setError(null);
        }
        return true;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        Log.i("current date", "SELECTED: year" + year + "   month :" + monthOfYear + "   day:" + dayOfMonth);
        selectedDate = year + monthOfYear + dayOfMonth;
        if ((year > 0 || monthOfYear > 0 || dayOfMonth > 0) && checkSelectedDate(year, monthOfYear, dayOfMonth)) {
            isDateSet = true;
            date.getEditText().setText("");
            date.getEditText().setText(year + "/" + monthOfYear + "/" + dayOfMonth);
            //TODO:add date to trip object

            //set Date
            myDate.setYear(year);
            myDate.setMonth(monthOfYear);
            myDate.setDay(dayOfMonth);

        } else {
            date.setError("Date need to be selected, Year, Month and the day must be the current or a coming date\n if Date is today Time must be current or upcoming time");
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        hourOfDay = (hourOfDay == 0) ? 12 : hourOfDay;
        selectedTime = hourOfDay + minute;
        Calendar calendar = Calendar.getInstance();
        int currYear = calendar.get(Calendar.YEAR);
        int currMonth = calendar.get(Calendar.MONTH);
        currMonth++;
        int currDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currDate = currYear + currMonth + currDay;
        int currTime = calendar.get(Calendar.HOUR_OF_DAY) + calendar.get(Calendar.MINUTE);

        if (selectedDate == currDate && selectedTime < currTime) {
            time.setError("if Date is Today , Time must be current or in the future");
            return;
        }
        if (hourOfDay > 0) {
            isTimeSet = true;
        }
        StringBuilder sb = new StringBuilder();
        if (minute < 10)
            sb.append("0").append(minute).toString();
        else
            sb.append(minute);
        time.getEditText().setText("");
        time.getEditText().setText(hourOfDay + ":" + sb.toString());
        //TODO: add to trip object

        //save time
        myDate.setHour(hourOfDay);
        myDate.setMinute(minute);
        myDate.setSecond(second);
    }

    //Recive result from place search fragment
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_TO_PLACE_REQUEST_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                endLon = place.getLatLng().longitude;
                endLat = place.getLatLng().latitude;
                /*Mannar*/
                myDirectionData.setEndLatitude(endLat);
                myDirectionData.setEndLongitude(endLon);
                /*Mannar*/
                Log.i(TAG, "onActivityResult: lon :" + startLon + "  lat:" + startLat);
                String toName = (place.getName() == null) ? to.getEditText().getText().toString() : place.getName();
                if (toName != null) {
                    to.getEditText().setText("");
                    to.getEditText().setTextColor(Color.BLACK);
                    to.getEditText().setText(toName);
                }
                Log.i(TAG, "Place: Address " + place.getAddress() + ", " + place.getLatLng());
            } else {
                Log.i(TAG, "Place: Error ");
                Log.i(TAG, "onActivityResult: lon :" + startLon + "  lat:" + startLat);


            }
        } else if (requestCode == AUTOCOMPLETE_FROM_PLACE_REQUEST_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                startLon = place.getLatLng().longitude;
                startLat = place.getLatLng().latitude;
                /*Mannar*/
                myDirectionData.setStartLatitude(startLat);
                myDirectionData.setStartLongitude(startLon);
                /*Mannar*/
                Log.i(TAG, "onActivityResult: lon :" + endLon + "  lat:" + endLat);
                String fromName = place.getName();
                if (fromName != null) {
                    from.getEditText().setText("");
                    from.getEditText().setTextColor(Color.BLACK);
                    from.getEditText().setText(fromName);
                }
                Log.i(TAG, "Place: Address " + place.getAddress() + ", " + place.getLatLng());
            } else {
                Log.i(TAG, "Place: Error ");
                Log.i(TAG, "onActivityResult: lon :" + endLon + "  lat:" + endLat);

            }
        }
    }

    private boolean checkSelectedDate(int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        int currYear = calendar.get(Calendar.YEAR);
        int currMonth = calendar.get(Calendar.MONTH);
        currMonth++;
        int currDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currDate = currYear + currMonth + currDay;
        int currTime = calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE);
        Log.i("current date", "Current: year" + currYear + "   month :" + currMonth + "   day:" + currDay);
        if (selectedTime > 0 && selectedTime < currTime && selectedDate == currDate) {
            return false;
        }
        if (year > currYear) {
            return true;
        } else if (year == currYear) {
            if (monthOfYear > currMonth) {
                return true;
            } else if (monthOfYear == currMonth) {
                if (dayOfMonth > currDay) {
                    return true;
                } else if (dayOfMonth == currDay) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updateUIwithPassedTrip(Trip trip) {
        title.getEditText().setText(trip.getTitle());
        to.getEditText().setText(trip.getEndLocation());
        from.getEditText().setText(trip.getStartLocation());
        startLat = trip.getStartLatitude();
        startLon = trip.getStartLongitude();
        endLat = trip.getEndtLatitude();
        endLon = trip.getEndLongitude();
        Log.i("args", "tripId: in update "+trip.getTripId());
        firestoreRepository.updateTrip(trip,trip).addOnSuccessListener(success->{
            Toast.makeText(requireActivity(),"Updated",Toast.LENGTH_LONG).show();
        });
    }

    /*Ashraf*/
    /*Manar*/
    private void startAlarm(Calendar calendar) {
        alarmMgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlarmReciever.class);
        Bundle args = new Bundle();
        args.putSerializable("myDirectionData", (Serializable) myDirectionData);
        args.putSerializable("MyNewTrip", newTrip);
        intent.putExtra("Data", args);
        alarmIntent = PendingIntent.getBroadcast(getActivity(), ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        }
    }

    private Calendar getEquivlentCalender(MyDate myDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, myDate.getHour());
        calendar.set(Calendar.MINUTE, myDate.getMinute());
        calendar.set(Calendar.SECOND, myDate.getSecond());
        calendar.set(Calendar.DAY_OF_MONTH, myDate.getDay());
        calendar.set(Calendar.YEAR, myDate.getYear());
        calendar.set(Calendar.MONTH, myDate.getMonth() - 1);
        return calendar;
    }
    /*Manar*/
}