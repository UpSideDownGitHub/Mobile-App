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

/*
 * this class will control the dice fragment, allowing the generation of random numbers
 */
public class DiceFragment extends Fragment {
    // Private varaibles
    private FragmentDiceBinding binding;

    /*
     * this method will run when the view is created and will initilaise all
     * elemtents as well as setting all of the listeners
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Create the view model
        DiceViewModel diceViewModel =
                new ViewModelProvider(this).get(DiceViewModel.class);

        // Get the binding and the root
        binding = FragmentDiceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // add listener to the generate number button
        binding.generatenumberButton.setOnClickListener(view -> generateNumber());

        // return root
        return root;
    }

    /*
     * generates the number either the default (1-6) or custom
     * between the user entered values
     */
    @SuppressLint("SetTextI18n")
    public void generateNumber()
    {
        // get the min and max
        String minString = binding.minvalueEntry.getText().toString();
        String maxString = binding.maxvaleEntry.getText().toString();
        int minValue = 1;
        int maxValue = 6;

        // try and convert the custom min&max to numbers
        try {
            if (!minString.isEmpty())
                minValue = Integer.parseInt(minString);
            if (!maxString.isEmpty())
                maxValue = Integer.parseInt(maxString);
        }
        catch (Exception e) {
            // if errors show error saying number to large
            Toast.makeText(getContext(), R.string.numbererror_tolarge, Toast.LENGTH_SHORT).show();
        }

        // tru and generate ranfom number
        try {
            // generate random between two custom values
            Random rand = new Random();
            int randomNumber = rand.nextInt((maxValue + 1)-minValue) + minValue;
            binding.diceanswerText.setText(Integer.toString(randomNumber));
        }
        catch (Exception e)
        {
            // generate random number between the default of 1-6 as min is larger so cant do custom 
            Random rand = new Random();
            int randomNumber = rand.nextInt((6 + 1)-1) + 1;
            binding.diceanswerText.setText(Integer.toString(randomNumber));
            // show error
            Toast.makeText(getContext(), R.string.numbererror_minlarger, Toast.LENGTH_SHORT).show();
        }
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