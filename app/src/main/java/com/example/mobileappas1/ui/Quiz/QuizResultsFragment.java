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
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.time.LocalDateTime;

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

    public void saveScore() {
        // create a file with the user name and the date and then save the score to it
        QuizResults scoreToSave = new QuizResults();
        scoreToSave.setScore(correctAnswers);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int playerID = sharedPref.getInt("playerID", 0);
        String name = getResources().getStringArray(R.array.usernames)[playerID];
        scoreToSave.setName(name);

        // create the Json file from the data
        Gson gson = new Gson();
        String json = gson.toJson(scoreToSave);

        // write the file
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                outputStream = getContext().openFileOutput("QuizResults_" + name + "_" + LocalDateTime.now(),
                        getContext().MODE_PRIVATE);
            }
            else
                outputStream = getContext().openFileOutput("QuizResults_" + name, getContext().MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Navigation.findNavController(getView()).navigate(R.id.navigation_quiz);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}