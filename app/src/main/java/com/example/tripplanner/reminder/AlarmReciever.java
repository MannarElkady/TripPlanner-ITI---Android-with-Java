package com.example.tripplanner.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


//Alarm BroadCast Reciever
public class AlarmReciever  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("alarm", "Alarm has Initiated Broadcast Receiver....");
        Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_SHORT).show();
    }
}
