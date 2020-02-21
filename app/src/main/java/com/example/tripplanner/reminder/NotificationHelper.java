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
    private NotificationManager notificationManager;

    public NotificationHelper(Context base) {
        super(base);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannel(String channelName, String channelID) {
        NotificationChannel notificationChannel = new NotificationChannel(channelID,channelName, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setLightColor(R.color.colorPrimary);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getNotificationManager().createNotificationChannel(notificationChannel);
    }

    public NotificationManager getNotificationManager(){
        if(notificationManager == null){
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public NotificationCompat.Builder buildNotification(String  channelID,String title,String message){

        return new NotificationCompat.Builder(getApplicationContext(),channelID)
                .setContentTitle(title).setContentText(message).setSmallIcon(R.drawable.ic_notification);
    }
}
