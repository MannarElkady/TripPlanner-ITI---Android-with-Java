package com.example.tripplanner.homescreen.homeview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.tripplanner.R;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.mynavToolBar);
        getSupportFragmentManager().beginTransaction().add(R.id.homeFragmentLayout, TripsHomeFragment.newInstance(),"AllTripFragment").commit();
        setMenu();
        configureNavigationDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_file, menu);
        return true;
    }

    private void setMenu() {
        setSupportActionBar(toolbar);

        //todo >> connect the humburger button with the navigation view

        //to get the right 3 dots button for settings
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //to get the back button
        //getSupportActionBar().setHomeButtonEnabled(true);

         getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
    }

    public interface OnRecycleItemClickListener {
        void onItemClick(View item,int position);
    }

    private void configureNavigationDrawer() {
        drawerLayout = findViewById(R.id.myDrawable);
        NavigationView navView = (NavigationView) findViewById(R.id.myNavView);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment f = null;
               /* int itemId = menuItem.getItemId();
                if (itemId == R.id.refresh) {
                    f = new ...Fragment();
                if (f != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, f);
                    transaction.commit();
                    drawerLayout.closeDrawers();
                    return true;
                }*/
                Toast.makeText(HomeActivity.this, "Item Pressed"+ menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
