package com.example.mobileappas1.ui.Language;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobileappas1.R;
import com.example.mobileappas1.databinding.FragmentLanguageBinding;
import com.example.mobileappas1.databinding.FragmentLanguageQuestionBinding;
import com.example.mobileappas1.ui.Calc.CalcViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LanguageQuestionFragment extends Fragment {
    private FragmentLanguageQuestionBinding binding;


    public int lang1ChosenOption = 0;
    public int lang2ChosenOption = 0;

    public int maxQuestions = 10;
    public int currentQuestion = 0;
    public int correctQuestions = 0;

    public String currentQuestions[];
    public String randomisedQuestions[];


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LanguageQuestionViewModel langViewModel =
                new ViewModelProvider(this).get(LanguageQuestionViewModel.class);

        binding = FragmentLanguageQuestionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // need to set the current question as well as setting the current answers
        currentQuestions = getResources().getStringArray(R.array.lang_q1);
        randomisedQuestions = getResources().getStringArray(R.array.lang_q1);
        updateAll();

        // Language 1
        binding.lang1Option1.setOnClickListener(view -> {
            disableAllLang1();
            binding.lang1Option1.setChecked(true);
            lang1ChosenOption = 1;
        });
        binding.lang1Option2.setOnClickListener(view -> {
            disableAllLang1();
            binding.lang1Option2.setChecked(true);
            lang1ChosenOption = 2;
        });
        binding.lang1Option3.setOnClickListener(view -> {
            disableAllLang1();
            binding.lang1Option3.setChecked(true);
            lang1ChosenOption = 3;
        });
        // Language 2
        binding.lang2Option1.setOnClickListener(view -> {
            disableAllLang2();
            binding.lang2Option1.setChecked(true);
            lang2ChosenOption = 1;
        });
        binding.lang2Option2.setOnClickListener(view -> {
            disableAllLang2();
            binding.lang2Option2.setChecked(true);
            lang2ChosenOption = 2;
        });
        binding.lang2Option3.setOnClickListener(view -> {
            disableAllLang2();
            binding.lang2Option3.setChecked(true);
            lang2ChosenOption = 3;
        });

        binding.langContineButton.setOnClickListener(view -> checkForCorrect());


        return root;
    }

    public void checkForCorrect()
    {
        if (lang2ChosenOption == 0 || lang1ChosenOption == 0)
        {
            Toast.makeText(getContext(), R.string.error_selectitem, Toast.LENGTH_SHORT).show();
            return;
        }
        if (currentQuestions[0] == randomisedQuestions[lang1ChosenOption - 1] &&
                currentQuestions[3] == randomisedQuestions[lang2ChosenOption + 2])
            correctQuestions++;
        currentQuestion++;

        // Updated Progress Items
        binding.langProgressbar.setProgress(currentQuestion, true);
        binding.langCurrentquestionText.setText(Integer.toString(currentQuestion) + "/10");

        if (currentQuestion == 10)
        {
            Bundle bundle = new Bundle();
            bundle.putInt("correct", correctQuestions);
            Navigation.findNavController(getView()).navigate(R.id.navigation_lang_result, bundle);
            return;
        }
        updateAll();
    }

    public void updateAll()
    {
        loadQuestions();
        randomiseList();
        binding.lang1Option1.setTextOff(randomisedQuestions[0]);
        binding.lang1Option1.setTextOn(randomisedQuestions[0]);
        binding.lang1Option1.setText(randomisedQuestions[0]);
        binding.lang1Option2.setTextOff(randomisedQuestions[1]);
        binding.lang1Option2.setTextOn(randomisedQuestions[1]);
        binding.lang1Option2.setText(randomisedQuestions[1]);
        binding.lang1Option3.setTextOff(randomisedQuestions[2]);
        binding.lang1Option3.setTextOn(randomisedQuestions[2]);
        binding.lang1Option3.setText(randomisedQuestions[2]);

        binding.lang2Option1.setTextOff(randomisedQuestions[3]);
        binding.lang2Option1.setTextOn(randomisedQuestions[3]);
        binding.lang2Option1.setText(randomisedQuestions[3]);
        binding.lang2Option2.setTextOff(randomisedQuestions[4]);
        binding.lang2Option2.setTextOn(randomisedQuestions[4]);
        binding.lang2Option2.setText(randomisedQuestions[4]);
        binding.lang2Option3.setTextOff(randomisedQuestions[5]);
        binding.lang2Option3.setTextOn(randomisedQuestions[5]);
        binding.lang2Option3.setText(randomisedQuestions[5]);
    }

    public void randomiseList()
    {
        List<String> stringList = new ArrayList<>();
        List<String> stringList2 = new ArrayList<>();
        stringList.add(currentQuestions[0]);
        stringList.add(currentQuestions[1]);
        stringList.add(currentQuestions[2]);
        stringList2.add(currentQuestions[3]);
        stringList2.add(currentQuestions[4]);
        stringList2.add(currentQuestions[5]);
        Collections.shuffle(stringList);
        Collections.shuffle(stringList2);
        randomisedQuestions[0] = stringList.get(0);
        randomisedQuestions[1] = stringList.get(1);
        randomisedQuestions[2] = stringList.get(2);
        randomisedQuestions[3] = stringList2.get(0);
        randomisedQuestions[4] = stringList2.get(1);
        randomisedQuestions[5] = stringList2.get(2);
    }
    public void loadQuestions()
    {
        switch (currentQuestion)
        {
            case 1:
                currentQuestions = getResources().getStringArray(R.array.lang_q2);
                break;
            case 2:
                currentQuestions = getResources().getStringArray(R.array.lang_q3);
                break;
            case 3:
                currentQuestions = getResources().getStringArray(R.array.lang_q4);
                break;
            case 4:
                currentQuestions = getResources().getStringArray(R.array.lang_q5);
                break;
            case 5:
                currentQuestions = getResources().getStringArray(R.array.lang_q6);
                break;
            case 6:
                currentQuestions = getResources().getStringArray(R.array.lang_q7);
                break;
            case 7:
                currentQuestions = getResources().getStringArray(R.array.lang_q8);
                break;
            case 8:
                currentQuestions = getResources().getStringArray(R.array.lang_q9);
                break;
            case 9:
                currentQuestions = getResources().getStringArray(R.array.lang_q10);
                break;
            default:
                break;
        }
    }

    public void disableAllLang1()
    {
        binding.lang1Option1.setChecked(false);
        binding.lang1Option2.setChecked(false);
        binding.lang1Option3.setChecked(false);
        lang1ChosenOption = 0;
    }
    public void disableAllLang2()
    {
        binding.lang2Option1.setChecked(false);
        binding.lang2Option2.setChecked(false);
        binding.lang2Option3.setChecked(false);
        lang2ChosenOption = 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}