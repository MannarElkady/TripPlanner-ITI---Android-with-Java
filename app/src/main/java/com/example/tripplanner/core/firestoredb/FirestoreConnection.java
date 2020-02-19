package com.example.tripplanner.core.firestoredb;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.core.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/*
Structure  :
    Collection Trips for all users trips
           @Param TRIP_COLLECTION
                 (Trips)
                    |
    Document userTrips with userId as identifier
            (UserId From login)
                    |
    Collection as TripsCollection per user
           @Param SUB_COLLECTION_OF_TRIPS
               (UserTrips)
                    |
    Trip document identified by tripId parameter

*/


public class FirestoreConnection {
    private static final String TAG = "FirestoreConnection";
    private static final String TRIP_COLLECTION = "Trips";
    private static final String SUB_COLLECTION_OF_TRIPS = "UserTrips";
    private static FirestoreConnection INSTANCE;
    private static User _user;

    private FirebaseFirestore db;

    DocumentReference tripsCollectionReference;

    private FirestoreConnection() {
        db = FirebaseFirestore.getInstance();
        tripsCollectionReference = db.collection(TRIP_COLLECTION).document(_user.getUserId());
        //setupCasheFirestore();
    }

    //get instance of FirestoreConnection
    public static FirestoreConnection getInstance(User user) {
        if (INSTANCE == null) {
            _user = user;
            INSTANCE = new FirestoreConnection();

        }
        return INSTANCE;
    }

    /*Mannar*/
    public void getAllCollectionDocuments(final List<String> documentNames) {
        /*tripsCollectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        documentNames.add(document.getId());
                    }
                    Log.i(TAG,documentNames.toString());
                    for(String s:documentNames) {
                        getDocumentByName(s);
                    }
                } else {
                    // Log.d(TAG, "Error getting documents: ", task.getException());
                    Log.i(TAG,task.getException().getMessage());
                }
            }
        });*/
    }

   /* public void addUserDocument(Users user){
        Map<String,Object> userMap= new HashMap<>();
        this.user= user;
        userMap.put("userId",user.getUserId());
        userMap.put("password",user.getPassword());
        userMap.put("Email",user.getUserEmail());
        userMap.put("userName",user.getUserName());
        userDocReference.set(userMap).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG,"Failed to add User");
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i(TAG,task.toString());
            }
        });
    }*/


    public void getDocumentByName(String documentName) {
        tripsCollectionReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.i(TAG, document.getData().toString());
                    } else {
                        Log.i(TAG, "No such document");
                    }
                } else {
                    Log.i("help", task.getException().getMessage());
                }
            }
        });


    }

    /*Mannar*/



    /*Reham*/

    /*Reham*/


    
    /*public void setupCasheFirestore(){
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        firebaseFirestore.setFirestoreSettings(settings);
    }*/


    /*Ashraf*/

    //add trip to user collection of trips
    public Task<Void> addTrip(Trip trip) {
        /*Map<String,Object> tripMap= new HashMap<>();
        tripMap.put("title",trip.getTitle());
        tripMap.put("date",trip.getTripDate());
        tripMap.put("startLocation",trip.getStartLocation());
        tripMap.put("endLocation",trip.getEndLocation());*/
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

        //to add trip document
        return tripsCollectionReference.collection(SUB_COLLECTION_OF_TRIPS)
                .document(trip.getTripId()).set(trip);
    }

    // get all user trips
    public Task<QuerySnapshot> getAllTrips() {
        //TODO: optimize getAllTrips to get just titles of trips
        return tripsCollectionReference.collection(SUB_COLLECTION_OF_TRIPS).get();
    }


    // delete trip from user trip collection
    public Task<Void> deleteTrip(Trip trip){
        return tripsCollectionReference.collection(SUB_COLLECTION_OF_TRIPS)
                                .document(trip.getTripId()).delete();
    }


    //update trip in user trip collection
    public Task<Void> updateTrip(Trip oldTrip, Trip newTrip){
        return tripsCollectionReference.collection(SUB_COLLECTION_OF_TRIPS)
                                .document(oldTrip.getTripId()).set(newTrip);
        //TODO: optimize update, to update the changed field only, not the whole document of a trip
    }


    //get a specific trip
    public Task<DocumentSnapshot> getTrip(Trip trip){
        Task<DocumentSnapshot> task = tripsCollectionReference.collection(SUB_COLLECTION_OF_TRIPS).document(trip.getTripId()).get();
        return task;
    }

    /*Ashraf*/
}















