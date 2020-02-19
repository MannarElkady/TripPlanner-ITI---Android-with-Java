package com.example.tripplanner.core.firestoredb;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tripplanner.MainActivity;
import com.example.tripplanner.R;
import com.example.tripplanner.core.model.Trip;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreConnection {
    FirebaseFirestore firebaseFirestore;
    public FirestoreConnection(FirebaseFirestore fs){
        firebaseFirestore= fs;
    }
    public void setAllCollectionDocumentsNames(final List<String> documentNames, String collectionName){
        firebaseFirestore.collection(collectionName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        documentNames.add(document.getId());
                    }
                    Log.i("docaa",documentNames.toString());
                    for(String s:documentNames) {
                        getDocumentByName("Trips",s);
                    }
                } else {
                    // Log.d(TAG, "Error getting documents: ", task.getException());
                    Log.i("docaa",documentNames.toString());
                }
            }
        });
    }

    public void getDocumentByName(String collectionName,String documentName){
        DocumentReference docRef = firebaseFirestore.collection(collectionName).document(documentName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.i("waw",document.getData().toString());
                    } else {
                        Log.i("help","No such document");
                    }
                } else {
                    Log.i("help",task.getException().getMessage());
                }
            }
        });


    }
    public void setupCasheFirestore(){
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        firebaseFirestore.setFirestoreSettings(settings);
    }
    public void addTrip(Trip trip){
        Map<String,Object> tripMap= new HashMap<>();
        tripMap.put("title",trip.getTitle());
        tripMap.put("date",trip.getTripDate());

        //to add document by random generated id
        /*firebaseFirestore.collection("Trips").add(tripMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(MainActivity.this, "Hi", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Hi", Toast.LENGTH_SHORT).show();
            }
        });*/

        //to update document
        firebaseFirestore.collection("Trips").document(trip.getTitle()).set(tripMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       // Toast.makeText(MainActivity.this, "Hi", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
             //   Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
