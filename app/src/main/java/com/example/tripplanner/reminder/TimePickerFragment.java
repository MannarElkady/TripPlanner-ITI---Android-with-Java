package com.example.tripplanner.reminder;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.tripplanner.R;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {
    /*Manar*/
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        //check
        Fragment fr=getActivity().getSupportFragmentManager().findFragmentById(R.id.fragments_functionality_layout).getChildFragmentManager().getFragments().get(0);
        return new TimePickerDialog(getActivity(),
                (TimePickerDialog.OnTimeSetListener) fr,hour,minute, DateFormat.is24HourFormat(getActivity()));
    }
    /*Manar*/
}
