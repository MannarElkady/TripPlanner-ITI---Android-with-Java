package com.example.tripplanner.pasttrips;

import com.example.tripplanner.core.constant.TripStatus;
import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.core.model.User;
import com.example.tripplanner.core.repository.remote.FirestoreRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
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
    }

    public void setPastTrips(){


        firestoreRepository.getTrips(TripStatus.FINISHED).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if(document.exists()){
                        Trip trip = document.toObject(Trip.class);
                        pastTripsTemp.add(trip);
                    }
                    pastTrips.postValue(pastTripsTemp);
                }
            }
        });

    }
    public LiveData<List<Trip>> getPastTrips(){
        return pastTrips;
    }
}
