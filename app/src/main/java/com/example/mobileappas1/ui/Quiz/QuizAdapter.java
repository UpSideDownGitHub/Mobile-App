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

import org.w3c.dom.Text;

import java.util.ArrayList;

/*
 * this class is the adapter for the recycler view that will show
 * all of the current quiz results
 */
public class QuizAdapter extends RecyclerView.Adapter<com.example.mobileappas1.ui.Quiz.QuizAdapter.ViewHolder2> {
    
    // Private variables
    private ArrayList<QuizDataHolder> quizDataHolder;
    private Context context;
    private FragmentActivity activity;
    private long currentNoteID;

    // Public variables
    public int globalPosition;

    /*
     * Constructor for initialization
     */
    public QuizAdapter(FragmentActivity givenActivity, Context context, ArrayList givenNotes) {
        this.context = context;
        this.quizDataHolder = givenNotes;
        this.activity = givenActivity;
    }

    /*
     * this class will initialize the view holder for the recycler view
     */
    @NonNull
    @Override
    public com.example.mobileappas1.ui.Quiz.QuizAdapter.ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create the new viewHolder and return it
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_list_item, parent, false);
        com.example.mobileappas1.ui.Quiz.QuizAdapter.ViewHolder2 viewHolder = new com.example.mobileappas1.ui.Quiz.QuizAdapter.ViewHolder2(view);
        return viewHolder;
    }

    /*
     * Bind the date to the specified position on screen
     */
    @Override
    public void onBindViewHolder(@NonNull com.example.mobileappas1.ui.Quiz.QuizAdapter.ViewHolder2 holder, int position) {
        // get the date at the current position
        final QuizDataHolder quizDataHolder1 = quizDataHolder.get(position);
        // set the name, score, and date from the data at the specified position
        holder.text.setText(quizDataHolder1.getName());
        holder.scoreText.setText(quizDataHolder1.getScore());
        holder.dateText.setText(quizDataHolder1.getDate());
    }

    /*
     * returns to total item count
     */
    @Override
    public int getItemCount() {
        return quizDataHolder.size();
    }

    /*
     * Initialize the view, getting all of the text views that will be edited
     */
    public class ViewHolder2 extends RecyclerView.ViewHolder {
        // private variables
        TextView text;
        TextView dateText;
        TextView scoreText;

        /*
         * constructor to initialize the view holder
         */
        public ViewHolder2(View view) {
            super(view);
            // get all of the text views
            text = (TextView) view.findViewById(R.id.quizresult_name);
            dateText = (TextView) view.findViewById(R.id.quizresult_date);
            scoreText = (TextView) view.findViewById(R.id.quizresult_score);
        }
    }

    /*
     * clear the list of items in the current list
     */
    public void clearList() {quizDataHolder.clear();}

    /*
     * add an item to the list of items to show on the recycler view
     */
    public void addValue(String value1, String value2, String value3) {
        quizDataHolder.add(new QuizDataHolder(value1, value2, value3));
    }

    /*
     *  update the data set to show the new added items
     */
    public void update(){ notifyDataSetChanged(); }
}