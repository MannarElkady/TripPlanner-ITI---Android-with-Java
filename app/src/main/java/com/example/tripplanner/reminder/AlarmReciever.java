package com.example.tripplanner.reminder;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.tripplanner.R;


//Alarm BroadCast Reciever
public class AlarmReciever  extends BroadcastReceiver {
    /*Manar*/
    public static final String channel1ID = "Channel1ID";
    public static final String channel1Name = "Channel1Name";
    Context context;
    NotificationHelper notificationHelper;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        notificationHelper = new NotificationHelper(context);
        Log.e("alarm", "Alarm has Initiated Broadcast Receiver....");
        Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_SHORT).show();
        sendOnChannel("Alarm","Alarm has Initiated Broadcast Receiver...");
        //to do open pop up with start, cancel and snooze buttons
        Intent intentDialog = new Intent(context,DialogActivity.class);
        intentDialog.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentDialog);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendOnChannel(String title, String message) {
        notificationHelper.createChannel(channel1Name,channel1ID);
        NotificationCompat.Builder noticitaion2 = notificationHelper.buildNotification(channel1ID,title,message);
        notificationHelper.getNotificationManager().notify(1,noticitaion2.build());
    }
    /*Manar*/
}
