package com.example.tripplanner.reminder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.tripplanner.R;

import java.text.DateFormat;
import java.util.Calendar;

//author manar

public class TestAlarmManagerWithNotification extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    /*Manar*/
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

    //Alarm Manager >> Background service >> Alarm Intent

    TextView setDateText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_alarm_manager_with_notification);
        /*Mannar */
        title = findViewById(R.id.title);
        desc = findViewById(R.id.body);
        setDateText = findViewById(R.id.dateTextView);

        ch1 = findViewById(R.id.startForegroundService);
        ch2 = findViewById(R.id.endForegroundService);
        notificationHelper = new NotificationHelper(this);
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


    }

    private void stopService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
    }

    private void startService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, serviceIntent);
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
        timePicker.show(getSupportFragmentManager(),"Choose a time...");
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
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReciever.class);
        alarmIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),alarmIntent);
        }
    }
    /*Manar*/
}
