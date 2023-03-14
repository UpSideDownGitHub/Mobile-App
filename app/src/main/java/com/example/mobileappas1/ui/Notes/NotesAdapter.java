package com.example.mobileappas1.ui.Notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappas1.R;

import java.util.ArrayList;

/*
 * this class is the adapter for the recycler view that will show
 * all of the current notes
 */
public class NotesAdapter extends RecyclerView.Adapter<com.example.mobileappas1.ui.Notes.NotesAdapter.ViewHolder> {
    // Private variables
    private ArrayList  noteList;
    private Context context;
    private FragmentActivity activity;
    private long currentNoteID;

    // Public variables
    public int globalPosition;

    /*
     * Constructor for initialization
     */
    public NotesAdapter(FragmentActivity givenActivity, Context context, ArrayList givenNotes) {
        this.context = context;
        this.noteList = givenNotes;
        this.activity = givenActivity;

    }

    /*
     * will initialize the view holder for the recycler view
     */
    @NonNull
    @Override
    public com.example.mobileappas1.ui.Notes.NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_item, parent, false);
        // Passing view to ViewHolder
        com.example.mobileappas1.ui.Notes.NotesAdapter.ViewHolder viewHolder = new com.example.mobileappas1.ui.Notes.NotesAdapter.ViewHolder(view);

        // set on click lister so that if pressed can find which button item in the view holder is pressed
        view.setOnClickListener(v -> {
            // get the position of the adapter
            globalPosition = viewHolder.getAbsoluteAdapterPosition();
            int pos = globalPosition;
            // get the index of the item pressed in the note list
            int index = noteList.indexOf(noteList.get(pos));
            // set the current index in shared preferences
            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("currentNoteID", index);
            editor.apply();

            // load the edit notes fragment to edit the clicked note
            Navigation.findNavController(view).navigate(R.id.navigation_edit_notes);
            // updated the view holder
            update();
        });

        // return the view holder
        return viewHolder;
    }

    /*
     * Bind the date to the specified position on screen
     */    
    @Override
    public void onBindViewHolder(@NonNull com.example.mobileappas1.ui.Notes.NotesAdapter.ViewHolder holder, int position) {
        // set the text of the current item
        holder.text.setText((String) noteList.get(position));
    }

    /*
     * returns to total item count
     */
    @Override
    public int getItemCount() {
        return noteList.size();
    }

     /*
     * Initialize the view, getting all of the text views that will be edited
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        // variables
        TextView text;

        /*
         * constructor to initialize the view holder
         */
        public ViewHolder(View view) {
            super(view);
            // get the text view
            text = (TextView) view.findViewById(R.id.note_item);
        }
    }

    /*
     * clear the list of items in the current list
     */
    public void clearList(){ noteList.clear(); }

    /*
     * add an item to the list of items to show on the recycler view
     */
    public void addValue(String value){ noteList.add(value); }

    /*
     *  update the data set to show the new added items
     */
    public void update(){ notifyDataSetChanged(); }
}