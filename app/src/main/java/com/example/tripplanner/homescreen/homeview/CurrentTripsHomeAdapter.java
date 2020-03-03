package com.example.tripplanner.homescreen.homeview;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplanner.R;
import com.example.tripplanner.core.model.Trip;
import java.util.List;

//author manar
public class CurrentTripsHomeAdapter extends RecyclerView.Adapter<CurrentTripsHomeAdapter.TripHolder> {
    private Context ctx;
    private List<Trip> myTripList;
    private View generatedRow;
    private CurrentTripsHomeFragment.OnRecycleItemClickListener recycleListener;
    public CurrentTripsHomeAdapter(Context context, List<Trip> myTripList, CurrentTripsHomeFragment.OnRecycleItemClickListener listener){
        ctx = context;
        this.myTripList = myTripList;
        recycleListener = listener;
    }
    @Override
    public CurrentTripsHomeAdapter.TripHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(ctx);
        generatedRow = layoutInflater.inflate(R.layout.one_row_trip_layout,parent,false);
        TripHolder tripHolder = new TripHolder(generatedRow);
        return tripHolder;
    }

    @Override
    public void onBindViewHolder(CurrentTripsHomeAdapter.TripHolder holder, int position) {
        holder.title.setText(myTripList.get(position).getTitle());
        holder.date.setText(myTripList.get(position).getTripDate());
        holder.locationFromTo.setText("From:  "+myTripList.get(position).getStartLocation()
                +"  to: "+myTripList.get(position).getEndLocation());
        holder.date.setTypeface(holder.date.getTypeface(), Typeface.BOLD);
        holder.locationFromTo.setTextSize(14f);
        holder.locationFromTo.setTypeface(holder.title.getTypeface(), Typeface.BOLD);
        holder.title.setTypeface(holder.title.getTypeface(), Typeface.BOLD);
        if(myTripList.get(position).getTripTime() != null)
            holder.time.setText(myTripList.get(position).getTripTime());
        else
            holder.time.setText("12:00");
        holder.time.setTypeface(holder.title.getTypeface(), Typeface.BOLD);
        //holder.tripImage.setImageResource(R.drawable.ic_book_bag);
        //add listener to each item
        holder.bind(holder.itemView,recycleListener);
    }

    @Override
    public int getItemCount() {
        return myTripList.size();
    }

    public class TripHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        TextView locationFromTo;
        ImageView tripImage;
        TextView time;
        public TripHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.trip_title_textview);
            //this.tripImage = itemView.findViewById(R.id.trip_imageview);
            this.date = itemView.findViewById(R.id.date_textview);
            this.locationFromTo = itemView.findViewById(R.id.tripFromToLocationOverview);
            this.time = itemView.findViewById(R.id.time_textview);
        }
        public void bind(View item, CurrentTripsHomeFragment.OnRecycleItemClickListener listener) {
           // name.setText(item.name);
            //Picasso.with(itemView.getContext()).load(item.imageUrl).into(image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item,getAdapterPosition());
                }
            });
        }
    }

    }
