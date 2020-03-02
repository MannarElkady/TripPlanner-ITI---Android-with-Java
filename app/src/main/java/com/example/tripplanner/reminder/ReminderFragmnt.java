package com.example.tripplanner.reminder;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.tripplanner.R;

import java.text.DateFormat;
import java.util.Calendar;

//author manar

public class ReminderFragmnt extends Fragment implements TimePickerDialog.OnTimeSetListener{

    private ReminderViewModel mViewModel;

    public static ReminderFragmnt newInstance() {
        return new ReminderFragmnt();
    }

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    public static final int ALARM_REQUEST_CODE=101;

    EditText title;
    EditText desc;
    Button ch1;
    Button ch2;
    NotificationHelper notificationHelper;
    public static final String channel1ID = "Channel1ID";
    public static final String channel1Name = "Channel1Name";
    TextView setDateText;
Button button;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.test_alarm_manager_with_notification, container, false);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.body);
        setDateText = view.findViewById(R.id.dateTextView);
        button = view.findViewById(R.id.addDateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker(v);
            }
        });
        ch1 = view.findViewById(R.id.startForegroundService);
        ch2 = view.findViewById(R.id.endForegroundService);
        notificationHelper = new NotificationHelper(getActivity());
        ch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
            }
        });
        ch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ReminderViewModel.class);
        // TODO: Use the ViewModel
    }

    private void stopService() {
        Intent serviceIntent = new Intent(getActivity(), ForegroundService.class);
        getActivity().stopService(serviceIntent);
    }

    private void startService() {
        Intent serviceIntent = new Intent(getContext(), ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(getContext(), serviceIntent);
    }

    private void sendOnChannel(String title, String message) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationHelper.createChannel(channel1Name,channel1ID);
        }
        NotificationCompat.Builder noticitaion2 = notificationHelper.buildNotification(channel1ID,title,message);
        notificationHelper.getNotificationManager().notify(1,noticitaion2.build());
    }

    public void normalChannel(View view) {
        sendOnChannel(title.getText().toString(),desc.getText().toString());
    }

    public void openTimePicker(View view) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getActivity().getSupportFragmentManager(),"Choose a time...");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND,0);

        updateTime(calendar);
        startAlarm(calendar);
    }

    private void updateTime(Calendar calendar) {
        setDateText.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()));
    }

    // alarm manager >> Background service >> alarm Intent
    private void startAlarm(Calendar calendar) {
        alarmMgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlarmReciever.class);
        alarmIntent = PendingIntent.getBroadcast(getActivity(), ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),alarmIntent);
        }
    }
}
