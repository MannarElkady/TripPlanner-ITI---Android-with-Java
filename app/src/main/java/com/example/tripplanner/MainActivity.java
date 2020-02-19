package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripplanner.core.firestoredb.FirestoreConnection;
import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.core.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    FirestoreConnection firestoreConnection;
    EditText title;
    EditText desc;
    final List<String> allDocumentsName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        desc = findViewById(R.id.description);
        title = findViewById(R.id.title);
        findViewById(R.id.clickme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users me = new Users("1","Manar","manara@gmail.com","123");
                firebaseFirestore = FirebaseFirestore.getInstance();
                firestoreConnection = new FirestoreConnection(firebaseFirestore);
                firestoreConnection.setupCasheFirestore();
                firestoreConnection.addUserDocument(new Users("1","Manar","123","manar@gmail.com"));
                firestoreConnection.addTrip(me,new Trip(MainActivity.this.title.getText().toString(), MainActivity.this.desc.getText().toString(),"giza","haram"));
                firestoreConnection.setAllCollectionDocumentsNames(allDocumentsName,"Trips");
                //Log.i("rere",list.toString());
            }
        });
    }
}
