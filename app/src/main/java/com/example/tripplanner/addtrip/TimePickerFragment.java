package com.example.tripplanner.addtrip;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public TimePickerListener mListener;

    public interface TimePickerListener{
        void onTimeSet(TimePicker view, int hour, int minute);
    }



    /*Manar*/
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(),(TimePickerDialog.OnTimeSetListener) getActivity(),hour,minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mListener.onTimeSet(view,hourOfDay,minute);
    }


    // To use
    //1- In Button Listener
    /*
        DialogFragment timePicker = new com.example.tripplanner.reminder.TimePickerFragment();
        timePicker.show(getSupportFragmentManager(),"Choose a time...");
     */

    //2- In the activity make activity implements TimePickerDialog.OnTimeSetListener and override this method:
    /*
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //Todo >> your Logic here
    }*/
    /*Manar*/
}
