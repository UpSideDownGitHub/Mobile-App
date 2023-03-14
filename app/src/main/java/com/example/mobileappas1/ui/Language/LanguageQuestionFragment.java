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

/*
 * handles the question screen of the language activity
 */
public class LanguageQuestionFragment extends Fragment {
    // Private varaibles
    private FragmentLanguageQuestionBinding binding;

    // Public Varaibles
    public int lang1ChosenOption = 0;
    public int lang2ChosenOption = 0;
    public int maxQuestions = 10;
    public int currentQuestion = 0;
    public int correctQuestions = 0;
    public String currentQuestions[];
    public String randomisedQuestions[];

    /*
     * this method will run when the view is created and will initilaise all
     * elemtents as well as setting all of the listeners
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Create the view model   
        LanguageQuestionViewModel langViewModel =
                new ViewModelProvider(this).get(LanguageQuestionViewModel.class);

        // Get the binding and the root
        binding = FragmentLanguageQuestionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // get the current qustions as well as the randomised ones
        currentQuestions = getResources().getStringArray(R.array.lang_q1);
        randomisedQuestions = getResources().getStringArray(R.array.lang_q1);
        // update all of the UI elements
        updateAll();

        // set the on click listeners for language one toggles
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
        // set the on clock listeners for language two toggles
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

        // add click listener for the continue button
        binding.langContineButton.setOnClickListener(view -> checkForCorrect());

        // return root
        return root;
    }

    /*
     * checks if the correct answer has been entered
     */
    public void checkForCorrect()
    {
        // if user has not selected both options error
        if (lang2ChosenOption == 0 || lang1ChosenOption == 0)
        {
            // error and stop
            Toast.makeText(getContext(), R.string.error_selectitem, Toast.LENGTH_SHORT).show();
            return;
        }
        // if got question correct increment correctQuestions
        if (currentQuestions[0] == randomisedQuestions[lang1ChosenOption - 1] &&
                currentQuestions[3] == randomisedQuestions[lang2ChosenOption + 2])
            correctQuestions++;
        // go to the next question
        currentQuestion++;

        // Updated Progress Items
        binding.langProgressbar.setProgress(currentQuestion, true);
        binding.langCurrentquestionText.setText(Integer.toString(currentQuestion) + "/10");

        // if on the last question then load the resutls screen
        if (currentQuestion == 10)
        {
            // pass the ammount of correct questions
            Bundle bundle = new Bundle();
            bundle.putInt("correct", correctQuestions);
            Navigation.findNavController(getView()).navigate(R.id.navigation_lang_result, bundle);
            return;
        }
        // update the UI
        updateAll();
    }

    /*
     * updates all of the UI elementsto show the current question
     */
    public void updateAll()
    {
        // load the questions and randomise them
        loadQuestions();
        randomiseList();
        // set the toggles to show the current question answer infroamtion
        // lang 1
        binding.lang1Option1.setTextOff(randomisedQuestions[0]);
        binding.lang1Option1.setTextOn(randomisedQuestions[0]);
        binding.lang1Option1.setText(randomisedQuestions[0]);
        binding.lang1Option2.setTextOff(randomisedQuestions[1]);
        binding.lang1Option2.setTextOn(randomisedQuestions[1]);
        binding.lang1Option2.setText(randomisedQuestions[1]);
        binding.lang1Option3.setTextOff(randomisedQuestions[2]);
        binding.lang1Option3.setTextOn(randomisedQuestions[2]);
        binding.lang1Option3.setText(randomisedQuestions[2]);
        // lang 2
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

    /*
     * randomises the question answes so they are different each time
     */
    public void randomiseList()
    {
        // convert to list (2 for the lang 1 & lang 2)
        List<String> stringList = new ArrayList<>();
        List<String> stringList2 = new ArrayList<>();
        // add all elements to list
        stringList.add(currentQuestions[0]);
        stringList.add(currentQuestions[1]);
        stringList.add(currentQuestions[2]);
        stringList2.add(currentQuestions[3]);
        stringList2.add(currentQuestions[4]);
        stringList2.add(currentQuestions[5]);
        // shuffle the lists
        Collections.shuffle(stringList);
        Collections.shuffle(stringList2);
        // set the randomise questions list
        randomisedQuestions[0] = stringList.get(0);
        randomisedQuestions[1] = stringList.get(1);
        randomisedQuestions[2] = stringList.get(2);
        randomisedQuestions[3] = stringList2.get(0);
        randomisedQuestions[4] = stringList2.get(1);
        randomisedQuestions[5] = stringList2.get(2);
    }
    /*
     * loads the questions from the resources
     */
    public void loadQuestions()
    {
        // swtich for the current question
        switch (currentQuestion)
        {
            // load the questions for the current question the player is on
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

    /*
     * turn off all toggles for lang 1
     */
    public void disableAllLang1()
    {
        binding.lang1Option1.setChecked(false);
        binding.lang1Option2.setChecked(false);
        binding.lang1Option3.setChecked(false);
        lang1ChosenOption = 0;
    }
    /*
     * turn off all toggles for lang 2
     */
    public void disableAllLang2()
    {
        binding.lang2Option1.setChecked(false);
        binding.lang2Option2.setChecked(false);
        binding.lang2Option3.setChecked(false);
        lang2ChosenOption = 0;
    }

     /*
     * handles destroying the view correctly
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}