package com.example.tripplanner.reminder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;

import com.example.tripplanner.R;

public class DialogActivity extends AppCompatActivity {
    Context context;
    AlertDialog mAlertDialog = null;
    AlertDialog.Builder mAlertDialogBuilder = null;
    MediaPlayer mediaPlayer;
    TimeUpAlertDialog timeUpAlertDialog = new TimeUpAlertDialog();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow ().setBackgroundDrawableResource (android.R.color.transparent);

    }
    @Override
    protected void onStart() {
        super.onStart();
        // to play local audio
       // mediaPlayer = MediaPlayer.create(this, R.raw.reminder_alarm);
      //  mediaPlayer.start();
        //show alert dialog;
        timeUpAlertDialog.showAlertDialog(context, "Time is Up, Would you like to start your trip?",
                "click START to navigate to your trip");
    }

    @Override
    protected void onStop() {
        super.onStop();
        timeUpAlertDialog.destroyAllertDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeUpAlertDialog = null;
    }
}
