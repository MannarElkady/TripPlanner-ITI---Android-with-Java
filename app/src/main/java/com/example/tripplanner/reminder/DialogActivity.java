package com.example.tripplanner.reminder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;

import com.example.tripplanner.R;
import com.example.tripplanner.core.constant.TripStatus;
import com.example.tripplanner.core.model.MyDirectionData;
import com.example.tripplanner.core.model.Trip;

public class DialogActivity extends AppCompatActivity {
    Context context;
    AlertDialog mAlertDialog = null;
    Trip currentTrip;
    int INTENTREQUESTCODE = 5;
    AlertDialog.Builder mAlertDialogBuilder = null;
    MediaPlayer mediaPlayer;
    TimeUpAlertDialog timeUpAlertDialog = new TimeUpAlertDialog();
    private ReminderViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReminderViewModel.class);
        this.context = this;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow ().setBackgroundDrawableResource (android.R.color.transparent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Data");
        currentTrip= ((Trip)bundle.getSerializable("MyNewTrip"));
        timeUpAlertDialog.showAlertDialog(context,currentTrip,
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
        //timeUpAlertDialog = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == INTENTREQUESTCODE) {
            if (resultCode == RESULT_OK) {
                Trip tripToUpdate = new Trip(currentTrip.getTitle(),currentTrip.getTripDate(),currentTrip.getStartLocation(),currentTrip.getEndLocation()
                        ,currentTrip.getStartLatitude(),currentTrip.getStartLongitude(),currentTrip.getEndtLatitude(),currentTrip.getEndLongitude(),currentTrip.getListOfNotes(), TripStatus.FINISHED);
                mViewModel.updateTrip(currentTrip,tripToUpdate);
            }
        }
    }
}
