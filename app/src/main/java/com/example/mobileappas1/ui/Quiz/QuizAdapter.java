package com.example.mobileappas1.ui.Quiz;

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

// Extends the Adapter class to RecyclerView.Adapter
// and implement the unimplemented methods
public class QuizAdapter extends RecyclerView.Adapter<com.example.mobileappas1.ui.Quiz.QuizAdapter.ViewHolder> {
    ArrayList  noteList;
    Context context;
    FragmentActivity activity;

    public int globalPosition;
    private long currentNoteID;


    // Constructor for initialization
    public QuizAdapter(FragmentActivity givenActivity, Context context, ArrayList givenNotes) {
        this.context = context;
        this.noteList = givenNotes;
        this.activity = givenActivity;

    }

    @NonNull
    @Override
    public com.example.mobileappas1.ui.Quiz.QuizAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout(Instantiates list_item.xml
        // layout file into View object)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_item, parent, false);

        // Passing view to ViewHolder
        com.example.mobileappas1.ui.Quiz.QuizAdapter.ViewHolder viewHolder = new com.example.mobileappas1.ui.Quiz.QuizAdapter.ViewHolder(view);

        view.setOnClickListener(v -> {
            globalPosition = viewHolder.getAbsoluteAdapterPosition();
            int pos = globalPosition;
            int index = noteList.indexOf(noteList.get(pos));
            Log.i("DEBUG", "You Pressed: " + index);


            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("currentNoteID", index);
            editor.apply();

            //Navigation.findNavController(view).navigate(R.id.navigation_edit_notes);
            update();
        });

        return viewHolder;
    }

    // Binding data to the into specified position
    @Override
    public void onBindViewHolder(@NonNull com.example.mobileappas1.ui.Quiz.QuizAdapter.ViewHolder holder, int position) {
        // TypeCast Object to int type
        //holder.text.setText("HELLO");
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
            text = (TextView) view.findViewById(R.id.quizresult_name);
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