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

public class LanguageResultFragment extends Fragment {

    private FragmentLanguageResultBinding binding;

    public int maxQustions = 10, correctAnswers;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LanguageResultViewModel langViewModel =
                new ViewModelProvider(this).get(LanguageResultViewModel.class);

        binding = FragmentLanguageResultBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        correctAnswers = getArguments().getInt("correct");

        // set the amount of question correct text
        String temp = correctAnswers + " of " + maxQustions;
        binding.langresultText.setText(temp);

        // set the percentage text
        temp = (float)correctAnswers/(float)maxQustions * 100f + "%";
        binding.langresultpercentText.setText(temp);

        binding.langcontinueButton.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.navigation_lang);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}