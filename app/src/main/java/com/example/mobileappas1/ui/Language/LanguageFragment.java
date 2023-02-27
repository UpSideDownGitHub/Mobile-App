package com.example.mobileappas1.ui.Language;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.example.mobileappas1.databinding.FragmentNotificationsBinding;
import com.example.mobileappas1.databinding.FragmentLanguageBinding;
import com.example.mobileappas1.ui.Calc.CalcViewModel;

public class LanguageFragment extends Fragment {

    private FragmentLanguageBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalcViewModel calcViewModel =
                new ViewModelProvider(this).get(CalcViewModel.class);

        binding = FragmentLanguageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}