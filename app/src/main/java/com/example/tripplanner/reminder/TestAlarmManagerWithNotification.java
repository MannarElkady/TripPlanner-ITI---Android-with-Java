package com.example.tripplanner.reminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tripplanner.R;
import com.example.tripplanner.core.model.Trip;

import java.util.Calendar;

public class TestAlarmManagerWithNotification extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_alarm_manager_with_notification);
        /*Mannar */

        title = findViewById(R.id.title);
        desc = findViewById(R.id.body);

        ch1 = findViewById(R.id.channel1Button);
        ch2 = findViewById(R.id.channel2Button);
        notificationHelper = new NotificationHelper(this);
        ch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOnChannel1(title.getText().toString(),desc.getText().toString());
            }
        });

        ch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOnChannel2(title.getText().toString(),desc.getText().toString());
            }
        });
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
        /*
            1st Param : Type of the Alarm
            2nd Parm : Time in milliseconds when the alarm will be triggered first
            3rd Param : Interval after which alarm will be repeated .
            4th Param : Pending Intent
            Note that we have changed the type to RTC_WAKEUP as we are using Wall clock time
        */
        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);



        /*Mannar */
    }

    private void sendOnChannel2(String title,String message) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationHelper.createChannel(channel2Name,channel2ID);
        }
        NotificationCompat.Builder noticitaion2 = notificationHelper.buildNotification(channel2ID,title,message);
        notificationHelper.getNotificationManager().notify(2,noticitaion2.build());

    }

    private void sendOnChannel1(String title,String message) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationHelper.createChannel(channel1Name,channel1ID);
        }
        NotificationCompat.Builder noticitaion2 = notificationHelper.buildNotification(channel1ID,title,message);
        notificationHelper.getNotificationManager().notify(1,noticitaion2.build());
    }
}
