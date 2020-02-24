package com.example.tripplanner.core.firestoredb;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.core.model.User;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QuerySnapshot;

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

    private DocumentReference tripsCollectionReference;

    private FirestoreConnection() {
        db = FirebaseFirestore.getInstance();
        tripsCollectionReference = db.collection(TRIP_COLLECTION).document(_user.getUserId());
    }

    //get instance of FirestoreConnection
    public static FirestoreConnection getInstance(User user) {
        if (INSTANCE == null) {
            _user = user;
            INSTANCE = new FirestoreConnection();

        }
        return INSTANCE;
    }

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
    /*Ashraf*/

    //add trip to user collection of trips
    public Task<Void> addTrip(Trip trip) {

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















