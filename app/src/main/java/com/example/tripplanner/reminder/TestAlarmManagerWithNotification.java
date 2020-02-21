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
import com.example.tripplanner.core.model.Trip;

import java.text.DateFormat;
import java.util.Calendar;

public class TestAlarmManagerWithNotification extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    /*Manar*/
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    public static final int ALARM_REQUEST_CODE=101;
    Trip trip;

    EditText title;
    EditText desc;
    Button ch1;
    Button ch2;
    NotificationHelper notificationHelper;
    public static final String channel1ID = "Channel1ID";
    public static final String channel1Name = "Channel1Name";
    public static final String channel2ID = "Channel2ID";
    public static final String channel2Name = "Channel2Name";

    //Alarm Manager >> Background service >> Alarm Intent

    TextView setDateText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_alarm_manager_with_notification);
        /*Mannar */

        title = findViewById(R.id.title);
        desc = findViewById(R.id.body);
        setDateText = findViewById(R.id.timeTextView);

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
        /*
        //1- get alarm service
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //Create Pending Intent >> Creating a PendingIntent is the most important part of scheduling an Alarm using AlarmManager
        //PendingIntent is an object which wraps another intent object.
        // We pass the pending intent to the AlarmManager which  basically gives AlarmManager the permission
        // to trigger the wrapped intent as if it is being triggered from your own app(Even if your application is killed)

        //2- The Wrapped Intent and create the broadcast reciever
        Intent intent = new Intent(this, AlarmReciever.class);

        //Flag_Update_CURRENT >> Flag indicating that if the described PendingIntent already exists,
        // then keep it but replace its extra data with what is in this new Intent.
        //3- The Pending Intent (Very Important)
        alarmIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //4- Choose Alarm Type and  Setting the Alarm
       // alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime() +60 * 1000, alarmIntent);
        //alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis(),AlarmManager.INTERVAL_HALF_HOUR,alarmIntent);


        //Another solution starting from step 4
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.AM_PM,1);
        calendar.set(Calendar.MINUTE, 45);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr.setExact(AlarmManager.RTC, calendar.getTimeInMillis(), alarmIntent);
        }
        /*
        1st Param : Type of the Alarm
        2nd Param : Time in milliseconds when the alarm will be triggered first
        3rd Param :Pending Intent
        Note that we have changed the type to RTC as we are using Wall clock time. Also device wont wake up
        when the alarm is triggered

                /*
            1st Param : Type of the Alarm
            2nd Parm : Time in milliseconds when the alarm will be triggered first
            3rd Param : Interval after which alarm will be repeated .
            4th Param : Pending Intent
            Note that we have changed the type to RTC_WAKEUP as we are using Wall clock time

        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);*/
        /*Mannar */
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
      //  calendar.set(Calendar.AM_PM,1);

        updateTime(calendar);
        startAlarm(calendar);
        /*
         setDateText.setText("Hour: "+hourOfDay+ " Minute: " + minute);
         long choosedDate  = (long) ((hourOfDay*60*1000)+(minute*1000));
         alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
         Intent intent = new Intent(this, AlarmReciever.class);
         alarmIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // alarmMgr.setExact(AlarmManager.RTC_WAKEUP, choosedDate ,alarmIntent);
        // alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);*/
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
