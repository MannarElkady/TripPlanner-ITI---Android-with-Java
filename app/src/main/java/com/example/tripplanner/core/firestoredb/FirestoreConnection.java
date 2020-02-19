package com.example.tripplanner.core.firestoredb;

import android.util.Log;
import androidx.annotation.NonNull;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Structure  : Document userTrips with userId as identifier >> Collection as TripsCollection per user >> Documents with the tripTitle as identifier

public class FirestoreConnection {
    FirebaseFirestore firebaseFirestore;
    String FIRETAG = "FireTag";
    public FirestoreConnection(FirebaseFirestore fs){
        firebaseFirestore= fs;
    }
    public void getAllCollectionDocuments(final List<String> documentNames, String collectionName){
        firebaseFirestore.collection(collectionName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        documentNames.add(document.getId());
                    }
                    Log.i(FIRETAG,documentNames.toString());
                    for(String s:documentNames) {
                        getDocumentByName("Trips",s);
                    }
                } else {
                    // Log.d(TAG, "Error getting documents: ", task.getException());
                    Log.i(FIRETAG,task.getException().getMessage());
                }
            }
        });
    }

    public void addUserDocument(Users user){
        Map<String,Object> userMap= new HashMap<>();
        userMap.put("userId",user.getUserId());
        userMap.put("password",user.getPassword());
        userMap.put("Email",user.getUserEmail());
        userMap.put("userName",user.getUserName());
        firebaseFirestore.collection("Trips").document(user.getUserId()).set(userMap).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(FIRETAG,"Failed to add User");
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i(FIRETAG,task.toString());
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
                        Log.i(FIRETAG,document.getData().toString());
                    } else {
                        Log.i(FIRETAG,"No such document");
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
    public void addTrip(Users user,Trip trip){
        Map<String,Object> tripMap= new HashMap<>();
        tripMap.put("title",trip.getTitle());
        tripMap.put("date",trip.getTripDate());
        tripMap.put("startLocation",trip.getStartLocation());
        tripMap.put("endLocation",trip.getEndLocation());
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
        firebaseFirestore.collection("Trips").document(user.getUserId())
                .collection("UserTrips").document(trip.getTitle()).set(tripMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //todo
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //todo
            }
        });
    }
}
