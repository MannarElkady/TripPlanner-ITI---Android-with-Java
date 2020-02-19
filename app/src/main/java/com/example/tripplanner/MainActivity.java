package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tripplanner.core.firestoredb.FirestoreConnection;
import com.example.tripplanner.homescreen.homeview.HomeActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseFirestore firebaseFirestore;
    FirestoreConnection firestoreConnection;
    EditText title;
    EditText desc;
    final List<String> allDocumentsName = new ArrayList<>();
    //todo >> array list may have duplicated document names inside it.. to be fix

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
      /*  desc = findViewById(R.id.description);
        title = findViewById(R.id.title);
        firestoreConnection = FirestoreConnection.getInstance(new User("1","mannar","ashraf@gmail.com","1234567"));


        /*findViewById(R.id.clickme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users me = new Users("1","Manar","manara@gmail.com","123");
                firebaseFirestore = FirebaseFirestore.getInstance();
                firestoreConnection = new FirestoreConnection(firebaseFirestore,me);
                firestoreConnection.addUserDocument(new Users("1","Manar","123","manar@gmail.com"));
                firestoreConnection.addTrip(new Trip(MainActivity.this.title.getText().toString(), MainActivity.this.desc.getText().toString(),"giza","haram"));
                firestoreConnection.getAllCollectionDocuments(allDocumentsName);
                //Log.i("rere",list.toString());
            }
        });*/
    }

    public void goToAddTripFragment(View view) {
    }
}
