package com.example.tripplanner.homescreen.homeview;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tripplanner.R;
import com.example.tripplanner.addtrip.TripAdditionDirections;
import com.example.tripplanner.core.model.Trip;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class CurrentTripsHomeFragment extends Fragment {

    private TripsHomeViewModel mViewModel;
    private CurrentTripsHomeAdapter currentTripsHomeAdapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    LinearLayout noTrips;

    public static CurrentTripsHomeFragment newInstance() {
        return new CurrentTripsHomeFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.getTripLiveData().observe(this,trips->{
            if(trips!=null&&trips.size() > 0){
                displayTrips(trips);
            }else{
                displayNoTrips();
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.home_trips_fragment, container, false);
        getActivity().findViewById(R.id.buttom_nav).setVisibility(VISIBLE);
        recyclerView = view.findViewById(R.id.trips_recyclerview);
        noTrips =  view.findViewById(R.id.no_trips_layout);
        view.findViewById(R.id.add_Trip_floating_point).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(CurrentTripsHomeFragmentDirections.actionCurrentTripsHomeFragmentToTripAddition(new Trip()));
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(TripsHomeViewModel.class);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);
    }

    public interface OnRecycleItemClickListener {
        void onItemClick(View item,int position);
    }

    public void displayNoTrips() {
        noTrips.setVisibility(VISIBLE);
        recyclerView.setVisibility(INVISIBLE);
    }
    public void displayTrips(List<Trip> trips){
        noTrips.setVisibility(INVISIBLE);
        recyclerView.setVisibility(VISIBLE);
        //create adapter with it's listener
        currentTripsHomeAdapter = new CurrentTripsHomeAdapter(getActivity(), trips, new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(View item, int position) {
                Toast.makeText(getContext(), "Item Clicked with position: "+ position, Toast.LENGTH_LONG).show();
                Navigation.findNavController(item).navigate(CurrentTripsHomeFragmentDirections.actionCurrentTripsHomeFragmentToTripDetailsFragment(trips.get(position)));
            }
        });
        recyclerView.setAdapter(currentTripsHomeAdapter);
    }


    //itemTouchHelper is for swip items to delete it
    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            //get swiped item position
            int index = viewHolder.getAdapterPosition();
            //get trip position
            Trip trip = mViewModel.allTrips.get(index);
            switch(direction) {
                case ItemTouchHelper.RIGHT: {

                    Toast.makeText(requireActivity(),"Edit Trip",Toast.LENGTH_LONG).show();
                    //delete from firestore and update Livedata
                    mViewModel.deleteTrip(trip, index);
                    //notify adapter with changes
                    currentTripsHomeAdapter.notifyDataSetChanged();
                }break;
                case ItemTouchHelper.LEFT:{
                    //TODO: get data and go to TripAddition
                    Toast.makeText(requireActivity(),"Edit Trip",Toast.LENGTH_LONG).show();
                    Navigation.findNavController(viewHolder.itemView).navigate(CurrentTripsHomeFragmentDirections
                            .actionCurrentTripsHomeFragmentToTripAdditionWithTripData(trip));
                }break;
            }
        }
    };
    @Override
    public void onStop() {
        super.onStop();
    }
}
