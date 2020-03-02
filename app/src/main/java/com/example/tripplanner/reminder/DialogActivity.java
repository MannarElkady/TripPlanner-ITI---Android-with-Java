package com.example.tripplanner.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;

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
    Bundle bundle;
    MyDirectionData myDirectionData;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReminderViewModel.class);
        this.context = this;
        intent = getIntent();
        if(intent !=null){
            if(intent.getBundleExtra("Data") !=null) {
                bundle = intent.getBundleExtra("Data");
                currentTrip = (Trip) bundle.getSerializable("MyNewTrip");
                myDirectionData = (MyDirectionData) bundle.getSerializable("myDirectionData");
            }
            else{
                currentTrip = (Trip) savedInstanceState.getSerializable("MyNewTrip");
                myDirectionData = (MyDirectionData) savedInstanceState.getSerializable("myDirectionData");
            }
        }
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow ().setBackgroundDrawableResource (android.R.color.transparent);
    }

    @Override
    protected void onStart() {
        super.onStart();


        timeUpAlertDialog.showAlertDialog(context,currentTrip,
                "click START to navigate to your trip",
                myDirectionData);
    }

    @Override
    protected void onStop() {
        super.onStop();
        timeUpAlertDialog.destroyAllertDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("MyNewTrip", currentTrip);
        outState.putSerializable("myDirectionData",myDirectionData);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == INTENTREQUESTCODE) {
            if (resultCode == RESULT_OK) {
                Trip tripToUpdate = new Trip(currentTrip.getTitle(),currentTrip.getTripDate(),currentTrip.getTripTime(),currentTrip.getStartLocation(),currentTrip.getEndLocation()
                        ,currentTrip.getStartLatitude(),currentTrip.getStartLongitude(),currentTrip.getEndtLatitude(),currentTrip.getEndLongitude(),currentTrip.getListOfNotes(), TripStatus.FINISHED);
                mViewModel.updateTrip(currentTrip,tripToUpdate);
            }
        }
    }
}
