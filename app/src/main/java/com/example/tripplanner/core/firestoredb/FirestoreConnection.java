package com.example.tripplanner.core.firestoredb;

import android.util.Log;

import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.core.model.User;

import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;


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
    private static final String NOTE_DOCUMENT = "NoteDocument";
    private static FirestoreConnection INSTANCE;
    private User _user;
    private static FirebaseFirestore db;

    private DocumentReference tripsCollectionReference;

    private FirestoreConnection(User _user) {
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
        Log.i(TAG, "FirestoreConnection: user id"+_user.getUserId());
    }

    //get instance of FirestoreConnection
    public static FirestoreConnection getInstance(User user) {
        if (INSTANCE == null) {
            INSTANCE = new FirestoreConnection(user);
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

    //add Notes to a trip
    /*public Task<DocumentReference> addNotes(HashMap<String,Note> notes, Trip trip){
        return tripsCollectionReference.collection(SUB_COLLECTION_OF_TRIPS).document(trip.getTripId()).collection(NOTE_DOCUMENT).document("Notes").add(notes);
    }*/
    // get all user trips
    public Task<QuerySnapshot> getAllTrips() {
        //TODO: optimize getAllTrips to get just titles of trips
        return tripsCollectionReference.collection(SUB_COLLECTION_OF_TRIPS).get();
    }


    //get document to listen on it
    public Query getTripsCollectionReference (String tripStatus){
        return tripsCollectionReference.collection(SUB_COLLECTION_OF_TRIPS).whereEqualTo("tripStatus",tripStatus);
    }
    // delete trip from user trip collection
    public Task<Void> deleteTrip(Trip trip){
        return tripsCollectionReference.collection(SUB_COLLECTION_OF_TRIPS)
                                .document(trip.getTripId()).delete();
    }


    public Query getPastTripsCollectionReference (String tripStatus){
        return tripsCollectionReference.collection(SUB_COLLECTION_OF_TRIPS).whereEqualTo("tripStatus",tripStatus);
    }



    //update trip in user trip collection
    public Task<Void> updateTrip(Trip oldTrip, Trip newTrip){
        Log.i("update", "oldTrip :"+oldTrip.getTripId()+"    newTrip:"+newTrip.getTripId());
        return tripsCollectionReference.collection(SUB_COLLECTION_OF_TRIPS)
                                .document(oldTrip.getTripId()).set(newTrip);
        //TODO: optimize update, to update the changed field only, not the whole document of a trip
    }

    @Override
    public Task<Void> updateTrip(Trip newTrip) {
        if(newTrip==null)
            return null;
        return tripsCollectionReference.collection(SUB_COLLECTION_OF_TRIPS)
                .document(newTrip.getTripId()).set(newTrip);
        //TODO: optimize update, to update the changed field only, not the whole document of a trip
    }


    //get a specific trip
    public Task<DocumentSnapshot> getTrip(Trip trip){
        Task<DocumentSnapshot> task = tripsCollectionReference.collection(SUB_COLLECTION_OF_TRIPS)
                .document(trip.getTripId())
                .get();
        
        return task;
    }

    @Override
    public DocumentReference getUserDocumentReference() {
        return tripsCollectionReference;
    }

    @Override
    public Task<QuerySnapshot> getTrips(String tripStatus) {
        return tripsCollectionReference.collection(SUB_COLLECTION_OF_TRIPS)
                .whereEqualTo("tripStatus",tripStatus).get();
    }

    //get data based on status
    public Task<QuerySnapshot> getTrip(String tripStatus){
        return tripsCollectionReference.collection(SUB_COLLECTION_OF_TRIPS)
                .whereEqualTo("tripStatus",tripStatus).get();
    }

    /*Ashraf*/
}















