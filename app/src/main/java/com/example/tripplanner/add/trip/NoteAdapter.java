package com.example.tripplanner.add.trip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplanner.R;
import com.example.tripplanner.core.model.Note;

import java.util.HashSet;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{

    private List<Note> listOfNotes;
    private Context context;
    private HashSet<Note> selectedNotes ;

    public NoteAdapter(Context _context, List<Note> notes) {
        listOfNotes = notes;
        selectedNotes = new HashSet<>();
        context = _context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        NoteViewHolder holder = new NoteViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = listOfNotes.get(position);
        holder.description.setText(note.getDescription());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedNotes.add(note);
                    //MainActivity.setCheckBox(m);
                    Toast.makeText(context, note.getDescription() + " checked", Toast.LENGTH_SHORT).show();
                } else {
                    selectedNotes.remove(note);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (listOfNotes != null) ? listOfNotes.size() : 0;
    }


    public void setNewAddedNotes(Note note){
        listOfNotes.add(note);
        notifyDataSetChanged();
    }
    public void deleteSet() {
        for (Note note : selectedNotes) {
            int index = listOfNotes.indexOf(note);
            listOfNotes.remove(index);
          //  this.notifyItemRemoved(index);
        }
        selectedNotes.clear();

    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        CheckBox checkBox;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.noteTitle);
            checkBox = itemView.findViewById(R.id.noteCheckBox);
        }
    }
}
