package com.example.mobileappas1.ui.Notes;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappas1.R;

import java.util.ArrayList;

// Extends the Adapter class to RecyclerView.Adapter
// and implement the unimplemented methods
public class NotesAdapter extends RecyclerView.Adapter<com.example.mobileappas1.ui.Notes.NotesAdapter.ViewHolder> {
    ArrayList  noteList;
    Context context;

    public int globalPosition;
    private long currentNoteID;


    // Constructor for initialization
    public NotesAdapter(Context context, ArrayList givenNotes) {
        this.context = context;
        this.noteList = givenNotes;
    }

    @NonNull
    @Override
    public com.example.mobileappas1.ui.Notes.NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout(Instantiates list_item.xml
        // layout file into View object)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_item, parent, false);

        // Passing view to ViewHolder
        com.example.mobileappas1.ui.Notes.NotesAdapter.ViewHolder viewHolder = new com.example.mobileappas1.ui.Notes.NotesAdapter.ViewHolder(view);

        view.setOnClickListener(v -> {
            globalPosition = viewHolder.getAbsoluteAdapterPosition();
            int pos = globalPosition;
            int index = noteList.indexOf(noteList.get(pos));
            Log.i("DEBUG", "You Pressed: " + index);
            update();
        });

        return viewHolder;
    }

    // Binding data to the into specified position
    @Override
    public void onBindViewHolder(@NonNull com.example.mobileappas1.ui.Notes.NotesAdapter.ViewHolder holder, int position) {
        // TypeCast Object to int type
        holder.text.setText((String) noteList.get(position));

        // SET THE COLOR OF THE SELECTED OBJECT
        if(position == globalPosition)
        {
            //change color like
            holder.itemView.setBackgroundColor(Color.RED);
            Log.i("DEBUG", "Note ID: " + currentNoteID);
        }
        else
        {
            //revert back to regular color
            holder.itemView.setBackgroundColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        // Returns number of items
        // currently available in Adapter
        return noteList.size();
    }

    // Initializing the Views
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public ViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.note_item);
        }
    }

    public void clearList()
    {
        noteList.clear();
    }

    public void addValue(String value)
    {
        noteList.add(value);
    }

    public void update()
    {
        notifyDataSetChanged();
    }
}