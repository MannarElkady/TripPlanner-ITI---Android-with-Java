package com.example.tripplanner.reminder;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.tripplanner.R;
import com.example.tripplanner.core.constant.TripStatus;
import com.example.tripplanner.core.model.MyDirectionData;
import com.example.tripplanner.core.model.Trip;

import java.util.Locale;

public class TimeUpAlertDialog {
    private Context context;
    private AlertDialog mAlertDialog = null;
    private MediaPlayer mediaPlayer;
    private ReminderViewModel mViewModel;
    private Trip tripToUpdate;
    private int INTENTREQUESTCODE = 5;

    public void showAlertDialog(Context context, Trip trip, String message, MyDirectionData myDirectionData) {
         mViewModel = new ViewModelProvider((AppCompatActivity)context).get(ReminderViewModel.class);
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
        mAlertDialog.setTitle(trip.getTitle());

        // Setting Dialog Message
        mAlertDialog.setMessage(message);
        mAlertDialog.setCancelable(false);
        mAlertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,context.getString(R.string.snooze_trip),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mAlertDialog.dismiss();
                        mediaPlayer.release();
                        mediaPlayer = null;
                        snoozeService();
                        Intent i = new Intent(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_HOME);
                        context.startActivity(i);
                    }
                });

        // Setting OK Button
        mAlertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.start_trip),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //start google map intent
                        dialog.dismiss();
                        mAlertDialog.dismiss();
                        /*String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f",myDirectionData.getStartLatitude(),
                                myDirectionData.getStartLongitude(),myDirectionData.getEndLatitude(),myDirectionData.getEndLongitude());*/
                        String uri = String.format(Locale.ENGLISH,"google.navigation:q=%f,%f",myDirectionData.getEndLatitude(),myDirectionData.getEndLongitude());
                        mAlertDialog = null;
                        stopSnoozeService();
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setPackage("com.google.android.apps.maps");
                        dialog.dismiss();
                        mediaPlayer.release();
                        tripToUpdate = new Trip(trip.getTitle(),trip.getTripDate(),trip.getTripTime(),trip.getStartLocation(),trip.getEndLocation()
                                ,trip.getStartLatitude(),trip.getStartLongitude(),trip.getEndtLatitude(),trip.getEndLongitude(),trip.getListOfNotes(),TripStatus.FINISHED);
                        mViewModel.updateTrip(trip,tripToUpdate);
                        ((AppCompatActivity) context).startActivityForResult(intent,INTENTREQUESTCODE);
                        ((Activity)context).finish();
                    }
                });
        mAlertDialog.setButton(DialogInterface.BUTTON_NEUTRAL,context.getString(R.string.cancel_trip),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //cancel trip here
                dialog.dismiss();
                mAlertDialog.dismiss();
                mAlertDialog = null;
                mediaPlayer.release();
                mediaPlayer = null;
                tripToUpdate = new Trip(trip.getTitle(),trip.getTripDate(),trip.getTripTime(),trip.getStartLocation(),trip.getEndLocation()
                        ,trip.getStartLatitude(),trip.getStartLongitude(),trip.getEndtLatitude(),trip.getEndLongitude(),trip.getListOfNotes(),TripStatus.CANCELED);
                mViewModel.updateTrip(trip,tripToUpdate);
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
