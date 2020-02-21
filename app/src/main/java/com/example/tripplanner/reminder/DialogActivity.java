package com.example.tripplanner.reminder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

public class DialogActivity extends AppCompatActivity {
    Context context;
    AlertDialog mAlertDialog = null;
    AlertDialog.Builder mAlertDialogBuilder = null;
    TimeUpAlertDialog timeUpAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        timeUpAlertDialog.showAlertDialog(context, "Time is Up, Would you like to start your trip?",
                "click START to navigate to your trip");
    }
}
