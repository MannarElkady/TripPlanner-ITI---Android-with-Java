package com.example.tripplanner.reminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.tripplanner.R;

public class NotificationHelper extends ContextWrapper {
    /*Manar*/
    private NotificationManager notificationManager;

    public NotificationHelper(Context base) {
        super(base);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannel(String channelName, String channelID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelID,channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setLightColor(R.color.colorPrimary);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getNotificationManager().createNotificationChannel(notificationChannel);
        }
    }

    public NotificationManager getNotificationManager(){
        if(notificationManager == null){
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public NotificationCompat.Builder buildNotification(String  channelID,String title,String message){
        return new NotificationCompat.Builder(getApplicationContext(),channelID)
                .setPriority(NotificationCompat.PRIORITY_MAX).setContentTitle(title).setContentText(message)
                //.setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_notification);
    }
    /*Manar*/
}
