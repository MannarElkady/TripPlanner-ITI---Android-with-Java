package com.example.tripplanner.reminder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;

import com.example.tripplanner.R;
import com.example.tripplanner.core.model.MyDirectionData;
import com.example.tripplanner.core.model.Trip;

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
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Data");
        timeUpAlertDialog.showAlertDialog(context,(((Trip)bundle.getSerializable("MyNewTrip")).getTitle()),
                "click START to navigate to your trip",
                (MyDirectionData) bundle.getSerializable("myDirectionData"));
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
