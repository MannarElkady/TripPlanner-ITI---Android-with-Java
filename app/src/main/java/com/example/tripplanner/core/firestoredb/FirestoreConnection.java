package com.example.tripplanner.core.firestoredb;

import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.core.model.User;

import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import javax.inject.Inject;

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


public class FirestoreConnection implements FirestoreContract {
    private static final String TAG = "FirestoreConnection";
    private static final String TRIP_COLLECTION = "Trips";
    private static final String SUB_COLLECTION_OF_TRIPS = "UserTrips";
    private static FirestoreConnection INSTANCE;
    private static User _user;

    private static FirebaseFirestore db;

    private DocumentReference tripsCollectionReference;

    private FirestoreConnection() {
        //enable caching
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();

        db = FirebaseFirestore.getInstance();

        //add caching settings
        db.setFirestoreSettings(settings);

        //get reference to the user document
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

    @Override
    public DocumentReference getUserDocumentReference() {
        return tripsCollectionReference;
    }

    //get data based on status
    public Task<QuerySnapshot> getTrip(String tripStatus){
        return tripsCollectionReference.collection(SUB_COLLECTION_OF_TRIPS).whereEqualTo("tripStatus",tripStatus).get();
    }

    /*Ashraf*/
}















