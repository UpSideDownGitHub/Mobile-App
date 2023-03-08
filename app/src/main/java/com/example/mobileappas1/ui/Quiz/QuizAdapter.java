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

// Extends the Adapter class to RecyclerView.Adapter
// and implement the unimplemented methods
public class QuizAdapter extends RecyclerView.Adapter<com.example.mobileappas1.ui.Quiz.QuizAdapter.ViewHolder2> {
    ArrayList<QuizDataHolder> quizDataHolder;
    Context context;
    FragmentActivity activity;

    public int globalPosition;
    private long currentNoteID;


    // Constructor for initialization
    public QuizAdapter(FragmentActivity givenActivity, Context context, ArrayList givenNotes) {
        this.context = context;
        this.quizDataHolder = givenNotes;
        this.activity = givenActivity;
    }

    @NonNull
    @Override
    public com.example.mobileappas1.ui.Quiz.QuizAdapter.ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_list_item, parent, false);
        com.example.mobileappas1.ui.Quiz.QuizAdapter.ViewHolder2 viewHolder = new com.example.mobileappas1.ui.Quiz.QuizAdapter.ViewHolder2(view);
        return viewHolder;
    }

    // Binding data to the into specified position
    @Override
    public void onBindViewHolder(@NonNull com.example.mobileappas1.ui.Quiz.QuizAdapter.ViewHolder2 holder, int position) {
        // TypeCast Object to int type
        final QuizDataHolder quizDataHolder1 = quizDataHolder.get(position);
        //holder.text.setText("HELLO");
        holder.text.setText(quizDataHolder1.getName());
        holder.scoreText.setText(quizDataHolder1.getScore());
        holder.dateText.setText(quizDataHolder1.getDate());
        Log.i("DEBUG", quizDataHolder1.getName());
        Log.i("DEBUG", quizDataHolder1.getScore());
        Log.i("DEBUG", quizDataHolder1.getDate());

    }

    @Override
    public int getItemCount() {
        // Returns number of items
        // currently available in Adapter
        return quizDataHolder.size();
    }

    // Initializing the Views
    public class ViewHolder2 extends RecyclerView.ViewHolder {
        TextView text;
        TextView dateText;
        TextView scoreText;

        public ViewHolder2(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.quizresult_name);
            dateText = (TextView) view.findViewById(R.id.quizresult_date);
            scoreText = (TextView) view.findViewById(R.id.quizresult_score);
        }
    }

    public void clearList() {quizDataHolder.clear();}

    public void addValue(String value1, String value2, String value3) {
        quizDataHolder.add(new QuizDataHolder(value1, value2, value3));
    }

    public void update()
    {
        notifyDataSetChanged();
    }
}