package com.example.mobileappas1.ui.Quiz;

import static java.time.format.DateTimeFormatter.ISO_DATE;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobileappas1.R;
import com.example.mobileappas1.databinding.FragmentQuizQuestionBinding;
import com.example.mobileappas1.databinding.FragmentQuizResultsBinding;
import com.example.mobileappas1.ui.Notes.Note;
import com.example.mobileappas1.ui.Notes.NotesData;
import com.example.mobileappas1.ui.Notes.User;
import com.example.mobileappas1.ui.Notes.Users;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 * this class is the results fragment and will handle the results screen
 */
public class QuizResultsFragment extends Fragment {

    // Private variables
    private FragmentQuizResultsBinding binding;
    private FileOutputStream outputStream;
    private int maxQustions = 10, correctAnswers, quizID;
    private QuizResults quizResults = new QuizResults();
    /*
     * is called when the view is created and will inistilse all the elemtents of the 
     * results screen
     */
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Create the view model
        QuizViewModel quizViewModel =
                new ViewModelProvider(this).get(QuizViewModel.class);
        
        // Get the binding and the root
        binding = FragmentQuizResultsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
            
        // get the ammount of correct answers from the arguments sent
        correctAnswers = getArguments().getInt("correct");
        
        // get the quiz ID from shared preferneces 
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        quizID = sharedPref.getInt("quizID", 0);

        // set the amount of question correct text
        String temp = correctAnswers + " of " + maxQustions;
        binding.quizresultText.setText(temp);

        // set the percentage text
        temp = (float)correctAnswers/(float)maxQustions * 100f + "%";
        binding.quizresultpercentText.setText(temp);

        // add click listeners to the two buttons
        binding.saveresultButton.setOnClickListener(view -> saveScore());
        binding.quizcontinueButton.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.navigation_quiz);
        });

        return root;
    }

    /*
     * Save the score that has been achived to the file with the correct quiz
     * so it can be shown in the correct leaderboard
     */
    public void saveScore() {

        // if there is not file then create one
        if (!isFilePresent(getContext(), "savedQuizResults.txt")) {
            createFile();
        }
        // read the file
        readFile();

        // get the player ID and the name of the player from the shared preferences
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int playerID = sharedPref.getInt("playerID", 0);
        String name = getResources().getStringArray(R.array.usernames)[playerID];

        // get all the data from the savedQuizResults
        ArrayList<Integer> scores = quizResults.getScore();
        ArrayList<Integer> types = quizResults.getType();
        ArrayList names = quizResults.getName();
        ArrayList dates = quizResults.getDate();
        // for all of the items in the data
        for (int i = 0; i < scores.size(); i++) {
            // if the score is less than the current score or equal to it then add the current score there
            if ((int)correctAnswers >= (int)scores.get(i)) {
                // ad all data to i position in the lists
                scores.add(i, correctAnswers);
                names.add(i, name);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    dates.add(i, LocalDateTime.now().format(ISO_DATE));
                else
                    dates.add(i, "N/A");
                types.add(i, quizID);
                break;
            }
            // if the last item in the list
            if (i == scores.size() - 1)
            {
                // TODO: make this so it adds to the end of the list as currently is adding 
                // one before the end
                
                // add elements at the final position
                scores.add(correctAnswers);
                names.add(name);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    dates.add(LocalDateTime.now().format(ISO_DATE));
                else
                    dates.add("N/A");
                types.add(quizID);
                break;
            }
        }
        // if there are no other elements in the list then just add
        if (scores.size() == 0)
        {
            // add the current score at the first position in the array
            scores.add(0, correctAnswers);
            names.add(0, name);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                dates.add(0, LocalDateTime.now().format(ISO_DATE));
            else
                dates.add(0, "N/A");
            types.add(0, quizID);
        }

        // update the quizresults to have the new values in them
        quizResults.setScore(scores);
        quizResults.setName(names);
        quizResults.setDate(dates);
        quizResults.setType(types);
        // write the new file to the storage
        writeFile();

        // go to the quiz main screen
        Navigation.findNavController(getView()).navigate(R.id.navigation_quiz);
    }


    public boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }

    public void writeFile()
    {
        // create the Json file from the data
        Gson gson = new Gson();
        String json = gson.toJson(quizResults);

        // write the file
        try {
            outputStream = getContext().openFileOutput("savedQuizResults.txt", getContext().MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFile()
    {
        // read the file
        FileInputStream fis = null;
        try {
            fis = getContext().openFileInput("savedQuizResults.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while (true) {
            try {
                if (!((line = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sb.append(line);
        }

        String json2 = sb.toString();

        Gson gson2 = new Gson();
        QuizResults data = gson2.fromJson(json2, QuizResults.class);
        // data is the data that has been read
        quizResults = data;
    }

    public void createFile()
    {
        // Set all of the default values (there are none for a empty high score)
        quizResults = new QuizResults();
        writeFile();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}