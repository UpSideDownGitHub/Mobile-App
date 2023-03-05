package com.example.mobileappas1.ui.Language;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobileappas1.R;
import com.example.mobileappas1.databinding.FragmentLanguageBinding;
import com.example.mobileappas1.databinding.FragmentLanguageQuestionBinding;
import com.example.mobileappas1.ui.Calc.CalcViewModel;

public class LanguageQuestionFragment extends Fragment {
    private FragmentLanguageQuestionBinding binding;


    public int lang1ChosenOption = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LanguageQuestionViewModel langViewModel =
                new ViewModelProvider(this).get(LanguageQuestionViewModel.class);

        binding = FragmentLanguageQuestionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


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


        return root;
    }

    public void disableAllLang1()
    {
        binding.lang1Option1.setChecked(true);
        binding.lang1Option2.setChecked(true);
        binding.lang1Option3.setChecked(true);
        lang1ChosenOption = 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}