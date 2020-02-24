package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.tripplanner.core.firestoredb.FirestoreConnection;
import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.core.model.User;
import com.example.tripplanner.homescreen.homeview.TripsHomeFragment;
import com.example.tripplanner.login.LoginFragment;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {

    private final static int ID_HOME=2;
    private final static int ID_HISTORY=1;
    private final static int ID_PROFILE=3;

    private static final String TAG = "MainActivity";
    FirestoreConnection firestoreConnection;
    MeowBottomNavigation buttomNavigation;
    public static User me = new User("1","Manar","manara@gmail.com","123");
    //todo >> array list may have duplicated document names inside it.. to be fix

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firestoreConnection = FirestoreConnection.getInstance(new User("1","mannar","ashraf@gmail.com","1234567"));

        /*findViewById(R.id.clickme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore = FirebaseFirestore.getInstance();
                firestoreConnection = FirestoreConnection.getInstance(me);
                firestoreConnection.addTrip(new Trip(MainActivity.this.title.getText().toString(), MainActivity.this.desc.getText().toString(),"giza","haram"));
            }
        });*/
        buttomNavigation = findViewById(R.id.buttom_nav);
        buttomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_history_black_24dp));
        buttomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_home_black_24dp));
        buttomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_account_circle_black_24dp));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragments_functionality_layout,new TripsHomeFragment()).commit();
        buttomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                Fragment toShowFragment = null;
                switch (model.getId()){
                    case ID_HOME:
                        toShowFragment = new TripsHomeFragment();
                        break;
                    case ID_PROFILE:
                        toShowFragment = new LoginFragment();
                        break;
                    case ID_HISTORY:
                        toShowFragment = new TripsHomeFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragments_functionality_layout,toShowFragment).commit();
                return null;
            }
        });
    }
}
