package com.example.tripplanner.pasttrips;

import android.util.Log;

import com.example.tripplanner.core.constant.TripStatus;
import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.core.model.User;
import com.example.tripplanner.core.repository.remote.FirestoreRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PastTripsViewModel extends ViewModel {
    //private FirestoreConnection firestoreConnection;
    private FirestoreRepository firestoreRepository;
    private List<Trip> pastTripsTemp;
    private MutableLiveData<List<Trip>> pastTrips;

    // Instead of MainActivity.me
    /*private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();*/

    public PastTripsViewModel() {
        //firestoreConnection = FirestoreConnection.getInstance(MainActivity.me);
        firestoreRepository = new FirestoreRepository(new User(FirebaseAuth.getInstance().getCurrentUser().getUid()));
        pastTripsTemp = new ArrayList<Trip>();
        pastTrips = new MutableLiveData<>();
        setPastTrips();
    }

    private void setPastTrips(){
        Log.i("past", "onEvent: setPastTrips ");
        firestoreRepository.getTripsCollectionReference(TripStatus.FINISHED).addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {

                } else {
                    if (queryDocumentSnapshots != null) {
                        List<DocumentChange> changes = queryDocumentSnapshots.getDocumentChanges();
                        for(DocumentChange documentChange : changes){
                            Log.i("changes", "onEvent: "+documentChange.getType() + "   "+documentChange.getDocument().get("title"));
                            Trip trip = documentChange.getDocument().toObject(Trip.class);
                            Log.i("past", "onEvent: Type "+documentChange.getType().toString());
                            switch(documentChange.getType()){
                                case ADDED :
                                    pastTripsTemp.add(trip);
                                    break;
                            }

                        }
                        pastTrips.postValue(pastTripsTemp);
                        Log.i("past", "pastTrips size : "+pastTripsTemp.size());
                    }
                }
            }
        });

        firestoreRepository.getTripsCollectionReference(TripStatus.CANCELED).addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {

                } else {
                    if (queryDocumentSnapshots != null) {
                        List<DocumentChange> changes = queryDocumentSnapshots.getDocumentChanges();
                        for(DocumentChange documentChange : changes){
                            Log.i("changes", "onEvent: "+documentChange.getType() + "   "+documentChange.getDocument().get("title"));
                            Trip trip = documentChange.getDocument().toObject(Trip.class);
                            Log.i("past", "onEvent: Type "+documentChange.getType().toString());
                            switch(documentChange.getType()){
                                case ADDED :
                                    pastTripsTemp.add(trip);
                                    break;
                            }

                        }
                        pastTrips.postValue(pastTripsTemp);
                        Log.i("past", "pastTrips size : "+pastTripsTemp.size());
                    }
                }
            }
        });

    }
    public LiveData<List<Trip>> getPastTrips(){
        return pastTrips;
    }

    private void delete(Trip trip) {
        for(Trip t : pastTripsTemp){
            if(t.getTripId().equals(trip.getTripId())){
                pastTripsTemp.remove(t);
                return;
            }
        }
        pastTrips.postValue(pastTripsTemp);

    }

    private void updateTrip(Trip trip) {
        for(Trip t : pastTripsTemp){
            if(t.getTripId().equals(trip.getTripId())){
                pastTripsTemp.remove(t);
                pastTripsTemp.add(trip);
                return;
            }
        }
        pastTrips.postValue(pastTripsTemp);
    }
}
