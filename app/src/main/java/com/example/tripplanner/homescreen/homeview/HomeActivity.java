package com.example.tripplanner.homescreen.homeview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.example.tripplanner.R;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.mynavToolBar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().add(R.id.homeFragmentLayout, TripsHomeFragment.newInstance(),"AllTripFragment").commit();
        setMenu();
    }
    private void setMenu() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_more_vert_black_24dp);
    }

    public interface OnRecycleItemClickListener {
        void onItemClick(View item,int position);
    }
}
