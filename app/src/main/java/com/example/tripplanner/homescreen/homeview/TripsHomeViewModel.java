package com.example.tripplanner.homescreen.homeview;

import android.util.Log;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.tripplanner.core.constant.TripStatus;
import com.example.tripplanner.core.firestoredb.FirestoreConnection;
import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.core.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
// @author (Mannar - Ashraf)


public class TripsHomeViewModel extends ViewModel {
    FirestoreConnection firestoreConnection;
    List<Trip> allTrips;
    private MutableLiveData<List<Trip>> allTripsLiveList = new MutableLiveData<>();

    public TripsHomeViewModel() {
        firestoreConnection = FirestoreConnection.getInstance(new User(FirebaseAuth.getInstance().getCurrentUser().getUid()));
        allTrips = new ArrayList<>();
        setTripsListener(TripStatus.UPCOMING);
    }

    /*public LiveData<List<Trip>> getCurrentTrips() {
        firestoreConnection.getTrip(TripStatus.UPCOMING).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.exists()) {
                        Trip temp = document.toObject(Trip.class);
                        allTrips.add(temp);
                    }
                    allTripsLiveList.postValue(allTrips);
                }
            }
        });

        return allTripsLiveList;
    }*/

    public LiveData<List<Trip>> getTripLiveData() {
        return allTripsLiveList;
    }

    public void setTripsListener(String tripStatus) {
        firestoreConnection.getTripsCollectionReference(tripStatus).addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {

                } else {
                    if (queryDocumentSnapshots != null) {
                        List<DocumentChange> changes = queryDocumentSnapshots.getDocumentChanges();
                        for(DocumentChange documentChange : changes){
                            Log.i("changes", "onEvent: "+documentChange.getType() + "   "+documentChange.getDocument().get("title"));
                            Trip trip = documentChange.getDocument().toObject(Trip.class);
                            switch(documentChange.getType()){
                                case ADDED :
                                    allTrips.add(trip);
                                    break;
                                case REMOVED:
                                    delete(trip);
                                    Log.i("changes", "size: "+allTrips.size());
                                    break;
                                case MODIFIED:
                                    updateTrip(trip);
                                    break;

                            }

                        }
                        allTripsLiveList.postValue(allTrips);
                    }
                }
            }
        });
    }

    private void updateTrip(Trip trip) {
        for(Trip t : allTrips){
            if(t.getTripId().equals(trip.getTripId())){
                allTrips.remove(t);
                allTrips.add(trip);
                return;
            }
        }
        allTripsLiveList.postValue(allTrips);
    }

    private void delete(Trip trip) {
        for(Trip t : allTrips){
            if(t.getTripId().equals(trip.getTripId())){
                allTrips.remove(t);
                return;
            }
        }
        allTripsLiveList.postValue(allTrips);
    }

    public void deleteTrip(Trip trip, int index) {
        firestoreConnection.deleteTrip(trip);
        allTripsLiveList.getValue().remove(index);
    }
}
