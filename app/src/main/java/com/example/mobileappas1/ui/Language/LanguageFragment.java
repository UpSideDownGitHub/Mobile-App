package com.example.mobileappas1.ui.Language;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.example.mobileappas1.databinding.FragmentNotificationsBinding;
import com.example.mobileappas1.R;
import com.example.mobileappas1.databinding.FragmentLanguageBinding;
import com.example.mobileappas1.ui.Calc.CalcViewModel;

public class LanguageFragment extends Fragment {

    private FragmentLanguageBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LanguageViewModel langViewModel =
                new ViewModelProvider(this).get(LanguageViewModel.class);

        binding = FragmentLanguageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.startButton.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.navigation_lang_question);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}