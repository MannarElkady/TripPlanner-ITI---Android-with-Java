package com.example.tripplanner.add.trip;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tripplanner.R;
import com.example.tripplanner.core.model.Note;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddTrip extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {


    private TextInputLayout to ;
    private ImageView time, date, add;
    private TextView timeTextView , dateTextView , done;
    private EditText title;
    private ChipGroup chipGroup ;
    private List<Note> notes ;
    private RecyclerView noteRecyclerView;
    private NoteAdapter noteAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notes = new ArrayList<>();
        noteAdapter = new NoteAdapter(getActivity().getApplicationContext(),notes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_trip, container, false);
        to = view.findViewById(R.id.to);
        time = view.findViewById(R.id.time);
        date = view.findViewById(R.id.date);
        timeTextView = view.findViewById(R.id.timeTextView);
        dateTextView = view.findViewById(R.id.dateTextView);
        chipGroup = view.findViewById(R.id.chip_group);
        add = view.findViewById(R.id.addNote);
        time.setOnClickListener((v)->{
            Calendar now = Calendar.getInstance();
            TimePickerDialog time = TimePickerDialog.newInstance(this,now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),true);
            time.show(getParentFragmentManager(),"TimePicker");
        });
        date.setOnClickListener(v->{
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog date = DatePickerDialog.newInstance(this,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            date.show(getParentFragmentManager(),"DatePicker");
        });

        add.setOnClickListener(v->{
            Note note = new Note("note 1",1);

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
        timeTextView.setText(hourOfDay+":"+minute);
        //TODO: add to trip object
    }
}
