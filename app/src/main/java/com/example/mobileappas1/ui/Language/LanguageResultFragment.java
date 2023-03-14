package com.example.mobileappas1.ui.Language;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobileappas1.R;
import com.example.mobileappas1.databinding.FragmentLanguageBinding;
import com.example.mobileappas1.databinding.FragmentLanguageResultBinding;

/*
 * resutls fragment that will handle showing all the information on the resutls screen
 */
public class LanguageResultFragment extends Fragment {
    // Private variables
    private FragmentLanguageResultBinding binding;

    // Public variables
    public int maxQustions = 10, correctAnswers;

    /*
     * this method will run when the view is created and will initilaise all
     * elemtents as well as setting all of the listeners
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Create the view model
        LanguageResultViewModel langViewModel =
                new ViewModelProvider(this).get(LanguageResultViewModel.class);
        
        // Get the binding and the root
        binding = FragmentLanguageResultBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        
        // get the ammount of correct answers from passed data
        correctAnswers = getArguments().getInt("correct");

        // set the amount of question correct text
        String temp = correctAnswers + " of " + maxQustions;
        binding.langresultText.setText(temp);

        // set the percentage text
        temp = (float)correctAnswers/(float)maxQustions * 100f + "%";
        binding.langresultpercentText.setText(temp);

        // attatck click listener to continue button
        binding.langcontinueButton.setOnClickListener(view -> {
            // go to the main language screen
            Navigation.findNavController(view).navigate(R.id.navigation_lang);
        });

        // return root
        return root;
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