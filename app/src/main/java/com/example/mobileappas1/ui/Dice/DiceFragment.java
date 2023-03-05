package com.example.mobileappas1.ui.Dice;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

//import com.example.mobileappas1.databinding.FragmentNotificationsBinding;
import com.example.mobileappas1.R;
import com.example.mobileappas1.databinding.FragmentDiceBinding;
import com.example.mobileappas1.ui.Calc.CalcViewModel;

import java.util.Random;

public class DiceFragment extends Fragment {

    private FragmentDiceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DiceViewModel diceViewModel =
                new ViewModelProvider(this).get(DiceViewModel.class);

        binding = FragmentDiceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.generatenumberButton.setOnClickListener(view -> generateNumber());


        return root;
    }

    @SuppressLint("SetTextI18n")
    public void generateNumber()
    {
        String minString = binding.minvalueEntry.getText().toString();
        String maxString = binding.maxvaleEntry.getText().toString();
        int minValue = 1;
        int maxValue = 6;

        try {
            if (!minString.isEmpty())
                minValue = Integer.parseInt(minString);
            if (!maxString.isEmpty())
                maxValue = Integer.parseInt(maxString);
        }
        catch (Exception e) {
            Toast.makeText(getContext(), R.string.numbererror_tolarge, Toast.LENGTH_SHORT).show();
        }

        try {
            Random rand = new Random();
            int randomNumber = rand.nextInt((maxValue + 1)-minValue) + minValue;
            binding.diceanswerText.setText(Integer.toString(randomNumber));
        }
        catch (Exception e)
        {
            Random rand = new Random();
            int randomNumber = rand.nextInt((6 + 1)-1) + 1;
            binding.diceanswerText.setText(Integer.toString(randomNumber));
            Toast.makeText(getContext(), R.string.numbererror_minlarger, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}