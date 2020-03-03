package com.example.tripplanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.tripplanner.core.firestoredb.FirestoreConnection;
import com.example.tripplanner.core.model.User;
import com.example.tripplanner.homescreen.homeview.CurrentTripsHomeFragmentDirections;
import com.example.tripplanner.pasttrips.PastTripsFragmentDirections;
import com.example.tripplanner.profile.ProfileFragmentDirections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {

    private final static int ID_HOME=2;
    private final static int ID_HISTORY=1;
    private final static int ID_PROFILE=3;
    public static int currentTab = ID_HOME;
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    FirebaseUser currentUser;
    private static final String TAG = "MainActivity";
    FirestoreConnection firestoreConnection;
    MeowBottomNavigation buttomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Reham*/

        if(checkSettings()){
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + this.getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        }

        /*Reham*/

        /*Manar*/
        //check user
        buttomNavigation = findViewById(R.id.buttom_nav);
        initBar();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            //buttomNavigation.setVisibility(View.GONE);
            Navigation.findNavController(this,R.id.fragments_functionality_layout).navigate(CurrentTripsHomeFragmentDirections.actionCurrentTripsHomeFragmentToLoginFragment());
        }
        else{
            User user = new User(currentUser.getUid());
            buttomNavigation.setVisibility(View.VISIBLE);
            firestoreConnection = FirestoreConnection.getInstance(user);
        }
        /*Manar*/

    }
    /*Manar*/
    private void initBar(){
        //temp
        //setting nav buttom
        buttomNavigation.add(new MeowBottomNavigation.Model(ID_HISTORY,R.drawable.ic_history_black_24dp));
        buttomNavigation.add(new MeowBottomNavigation.Model(ID_HOME,R.drawable.ic_home_black_24dp));
        buttomNavigation.add(new MeowBottomNavigation.Model(ID_PROFILE,R.drawable.ic_account_circle_black_24dp));
        //buttomNavigation.setVisibility(View.INVISIBLE);
        buttomNavigation.show(ID_HOME,true);
        buttomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case ID_HOME:
                        if(currentTab==ID_HOME+1){
                            //go from profile
                            Navigation.findNavController(MainActivity.this,R.id.fragments_functionality_layout).navigate(ProfileFragmentDirections.actionProfileFragmentToCurrentTripsHomeFragment());
                        }
                        else if(currentTab==ID_HOME-1){
                            //go from history
                            Navigation.findNavController(MainActivity.this,R.id.fragments_functionality_layout).navigate(PastTripsFragmentDirections.actionPastTripsFragmentToCurrentTripsHomeFragment());
                        }
                        currentTab = ID_HOME;
                        break;
                    case ID_PROFILE:
                        if(currentTab==ID_PROFILE-1){
                            //go from home
                            //Navigation.findNavController(MainActivity.this,R.id.fragments_functionality_layout).navigate(CurrentTripsHomeFragmentDirections.actionCurrentTripsHomeFragmentToReminderFragmnt());
                            Navigation.findNavController(MainActivity.this, R.id.fragments_functionality_layout).navigate(CurrentTripsHomeFragmentDirections.actionCurrentTripsHomeFragmentToProfileFragment());
                        }
                        else if(currentTab == ID_PROFILE-2){
                            //go from history
                            Navigation.findNavController(MainActivity.this,R.id.fragments_functionality_layout).navigate(PastTripsFragmentDirections.actionPastTripsFragmentToProfileFragment());
                        }
                        currentTab = ID_PROFILE;
                        break;
                    case ID_HISTORY:
                        if(currentTab==ID_HISTORY+1){
                            //go from home
                            Navigation.findNavController(MainActivity.this,R.id.fragments_functionality_layout).navigate(CurrentTripsHomeFragmentDirections.actionCurrentTripsHomeFragmentToPastTripsFragment());
                        }
                        else if(currentTab==ID_HISTORY+2){
                            //go from profile
                            Navigation.findNavController(MainActivity.this,R.id.fragments_functionality_layout).navigate(ProfileFragmentDirections.actionProfileFragmentToPastTripsFragment());
                        }
                        currentTab = ID_HISTORY;
                        break;
                }
                return null;
            }
        });
    }
    /*Manar*/
    /*Reham*/


    public boolean checkSettings(){
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
               if(checkSettings()){
                   Toast.makeText(this,"The Floating Icon Will Not Appear",Toast.LENGTH_SHORT).show();
               }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /*Reham*/
}