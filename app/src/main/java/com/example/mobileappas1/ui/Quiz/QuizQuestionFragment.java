package com.example.mobileappas1.ui.Quiz;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mobileappas1.R;
import com.example.mobileappas1.databinding.FragmentQuizBinding;
import com.example.mobileappas1.databinding.FragmentQuizQuestionBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class QuizQuestionFragment extends Fragment {

    private FragmentQuizQuestionBinding binding;

    private int currentQuestion, quizID, correctAnswers, answerChosen;
    private String[] questions;
    private String[] answers;
    private String[] currentAnswers;

    private List<Integer> imageAssets = new ArrayList<Integer>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QuizViewModel quizViewModel =
                new ViewModelProvider(this).get(QuizViewModel.class);

        binding = FragmentQuizQuestionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        currentQuestion = 0;


        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        quizID = sharedPref.getInt("quizID", 0);
        if (quizID == 1) {
            questions = getResources().getStringArray(R.array.q1_questions);
            answers = getResources().getStringArray(R.array.q1_answers);
        }
        else if (quizID == 2) {
            questions = getResources().getStringArray(R.array.q2_questions);
            answers = getResources().getStringArray(R.array.q2_answers);
        }
        else {
            questions = getResources().getStringArray(R.array.q3_questions);
            answers = getResources().getStringArray(R.array.q3_answers);
        }
        getImages();
        updateAll();


        binding.answerA.setOnClickListener(view -> {
            answerChosen = 1;
            currentQuestion++;
            checkForCorrect();
            updateAll();
        });
        binding.answerB.setOnClickListener(view -> {
            answerChosen = 2;
            currentQuestion++;
            checkForCorrect();
            updateAll();
        });
        binding.answerC.setOnClickListener(view -> {
            answerChosen = 3;
            currentQuestion++;
            checkForCorrect();
            updateAll();
        });
        binding.answerD.setOnClickListener(view -> {
            answerChosen = 4;
            currentQuestion++;
            checkForCorrect();
            updateAll();
        });

        return root;
    }

    public void checkForCorrect()
    {
        if (currentQuestion == 10)
        {
            Bundle bundle = new Bundle();
            bundle.putInt("correct", correctAnswers);
            Navigation.findNavController(getView()).navigate(R.id.navigation_quiz_results, bundle);
            return;
        }

        if(currentAnswers[answerChosen - 1] == answers[(currentQuestion-1) * 4])
            correctAnswers++;
    }

    public void updateAll()
    {
        if (currentQuestion == 10)
            return;
        // update possible answers
        currentAnswers = new String[]{answers[currentQuestion * 4], answers[currentQuestion * 4 + 1] ,
                answers[currentQuestion * 4 + 2] , answers[currentQuestion * 4 + 3] };
        List<String> stringList = Arrays.asList(currentAnswers);
        Collections.shuffle(stringList);
        currentAnswers = stringList.toArray(currentAnswers);

        // completion bar
        binding.quizProgress.setProgress(currentQuestion, true);

        // question text
        binding.questionText.setText(questions[currentQuestion]);

        // answers text
        binding.answerA.setText(currentAnswers[0]);
        binding.answerB.setText(currentAnswers[1]);
        binding.answerC.setText(currentAnswers[2]);
        binding.answerD.setText(currentAnswers[3]);

        // update the image
        binding.questionImage.setBackgroundResource(imageAssets.get(currentQuestion + (10 * (quizID - 1))));
    }

    public void getImages()
    {
        // Maths
        imageAssets.add(R.drawable.circumference);
        imageAssets.add(R.drawable.area_of_triangle);
        imageAssets.add(R.drawable.pythagorus);
        imageAssets.add(R.drawable.surfacce_of_sphere);
        imageAssets.add(R.drawable.quadratic);
        imageAssets.add(R.drawable.volume_of_cone);
        imageAssets.add(R.drawable.fma);
        imageAssets.add(R.drawable.surfacce_of_sphere);
        imageAssets.add(R.drawable.emc2);
        imageAssets.add(R.drawable.volume_of_cylinder);

        // History
        imageAssets.add(R.drawable.chernobyl);
        imageAssets.add(R.drawable.richard_wittington);
        imageAssets.add(R.drawable.john_adams);
        imageAssets.add(R.drawable.jane_seymour);
        imageAssets.add(R.drawable.howard_carter);
        imageAssets.add(R.drawable.robert_f_scott);
        imageAssets.add(R.drawable.europe);
        imageAssets.add(R.drawable.william_wallace);
        imageAssets.add(R.drawable.south_africa);
        imageAssets.add(R.drawable.ww2);

        // Geography
        imageAssets.add(R.drawable.world_map);
        imageAssets.add(R.drawable.malta);
        imageAssets.add(R.drawable.world_map);
        imageAssets.add(R.drawable.uk);
        imageAssets.add(R.drawable.uk);
        imageAssets.add(R.drawable.world_map);
        imageAssets.add(R.drawable.france);
        imageAssets.add(R.drawable.world_map);
        imageAssets.add(R.drawable.usa);
        imageAssets.add(R.drawable.uk);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}