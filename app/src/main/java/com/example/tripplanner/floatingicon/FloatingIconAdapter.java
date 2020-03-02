package com.example.tripplanner.floatingicon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tripplanner.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FloatingIconAdapter extends RecyclerView.Adapter<FloatingIconAdapter.MyViewHolder> {
    private ArrayList<String> notes;
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        View myView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myView = itemView;
        }

        public View getView(){
            return myView;
        }
    }

    public FloatingIconAdapter(ArrayList<String> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.one_note_row, parent, false);
        //parent.

        //Log.i("ADAPTER", "***********onCreateViewHolder");
        return new MyViewHolder(view);
    }

    /*@Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }*/

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        TextView textView = holder.getView().findViewById(R.id.floatingNoteTextView);
        textView.setText(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
