package com.example.mobileappas1.ui.Dice;

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
import com.example.mobileappas1.databinding.FragmentDiceBinding;
import com.example.mobileappas1.ui.Calc.CalcViewModel;

public class DiceFragment extends Fragment {

    private FragmentDiceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalcViewModel calcViewModel =
                new ViewModelProvider(this).get(CalcViewModel.class);

        binding = FragmentDiceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}