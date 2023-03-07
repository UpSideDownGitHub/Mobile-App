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

public class QuizResultsFragment extends Fragment {

    private FragmentQuizResultsBinding binding;
    FileOutputStream outputStream;

    private int maxQustions = 10, correctAnswers;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QuizViewModel quizViewModel =
                new ViewModelProvider(this).get(QuizViewModel.class);

        binding = FragmentQuizResultsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        correctAnswers = getArguments().getInt("correct");

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

    QuizResults quizResults = new QuizResults();

    public void saveScore() {

        // need to load the saved file

        if (!isFilePresent(getContext(), "savedQuizResults.txt")) {
            Log.i("DEBUG", "NO FILE");
            createFile();
        }
        readFile();

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int playerID = sharedPref.getInt("playerID", 0);
        String name = getResources().getStringArray(R.array.usernames)[playerID];

        ArrayList<Integer> scores = quizResults.getScore();
        ArrayList names = quizResults.getName();
        ArrayList dates = quizResults.getDate();
        for (int i = 0; i < scores.size(); i++) {
            // if the score is less than the current score or equal to it then add the current score there
            if ((int)correctAnswers >= (int)scores.get(i)) {
                scores.add(i, correctAnswers);
                names.add(i, name);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    dates.add(i, LocalDateTime.now().format(ISO_DATE));
                else
                    dates.add(i, "N/A");
                break;
            }
        }
        if (scores.size() == 0)
        {
            scores.add(0, correctAnswers);
            names.add(0, name);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                dates.add(0, LocalDateTime.now().format(ISO_DATE));
            else
                dates.add(0, "N/A");
        }

        quizResults.setScore(scores);
        quizResults.setName(names);
        quizResults.setDate(dates);
        writeFile();

        Log.i("DEBUG", quizResults.getName().get(0).toString());

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