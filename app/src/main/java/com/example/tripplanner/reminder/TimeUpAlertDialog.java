package com.example.tripplanner.reminder;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.tripplanner.R;

public class TimeUpAlertDialog {
    private Context context;
    private AlertDialog mAlertDialog = null;
    MediaPlayer mediaPlayer;

    public void showAlertDialog(Context context, String title, String message) {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            return;
        //already showing
        } else if (mAlertDialog != null) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
        this.context = context;
        mAlertDialog = new AlertDialog.Builder(context).create();

        // to play local audio
        mediaPlayer = MediaPlayer.create(context, R.raw.reminder_alarm);
        mediaPlayer.start();
        // Setting Dialog Title
        mAlertDialog.setTitle(title);

        // Setting Dialog Message
        mAlertDialog.setMessage(message);
        mAlertDialog.setCancelable(false);
        mAlertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,context.getString(R.string.snooze_trip),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mAlertDialog = null;
                        mediaPlayer.release();
                        mediaPlayer = null;
                        snoozeService();
                        ((Activity)context).finish();
                    }
                });

        // Setting OK Button
        mAlertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.start_trip),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //start google map intent
                        dialog.dismiss();
                        mAlertDialog = null;
                        stopSnoozeService();
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
                        dialog.dismiss();
                        mAlertDialog = null;
                        mediaPlayer.release();
                        mediaPlayer = null;
                        context.startActivity(intent);
                    }
                });
        mAlertDialog.setButton(DialogInterface.BUTTON_NEUTRAL,context.getString(R.string.cancel_trip),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //cancel trip here
                dialog.dismiss();
                mAlertDialog = null;
                mediaPlayer.release();
                mediaPlayer = null;
                stopSnoozeService();
                ((Activity)context).finish();

            }
        });
        // Showing Alert Message
       // mAlertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
        mAlertDialog.show();
    }

    private void snoozeService() {
        Intent serviceIntent = new Intent(context, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(context, serviceIntent);
    }

    private void stopSnoozeService() {
        Intent serviceIntent = new Intent(context, ForegroundService.class);
        context.stopService(serviceIntent);
    }

    public void destroyAllertDialog(){
        if(mAlertDialog!=null) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }
}
