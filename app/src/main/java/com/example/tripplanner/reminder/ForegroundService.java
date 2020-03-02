package com.example.tripplanner.reminder;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

import com.example.tripplanner.MainActivity;

//author manar

public class ForegroundService extends Service {
    /*Manar*/
    public static final String CHANNEL = "MyChannel";
    public static final int REQUEST_CODE = 1;
    private static int NOTIFICATION_ID = 3;
    public static final String NOTIFICATION_HEADER = "My Trips Need's Your Attention..";

    NotificationHelper notificationHelper;
    String input;

    public ForegroundService() {
        notificationHelper = new NotificationHelper(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        input = intent.getStringExtra("inputExtra");
        notificationHelper.createChannel(CHANNEL,CHANNEL);
        Intent notificationIntent = new Intent(this, DialogActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                REQUEST_CODE, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = notificationHelper.buildNotification(CHANNEL,NOTIFICATION_HEADER,input).setContentIntent(pendingIntent).build();
        startForeground(NOTIFICATION_ID,notification);
        //do heavy work on a background thread
        //stopSelf();
        return START_NOT_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
    /*Manar*/
}