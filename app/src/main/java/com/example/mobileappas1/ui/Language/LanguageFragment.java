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

/*
 * language fragment handles the main screen of the language activity
 */
public class LanguageFragment extends Fragment {
    // Private varaibles
    private FragmentLanguageBinding binding;

    /*
     * this method will run when the view is created and will initilaise all
     * elemtents as well as setting all of the listeners
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Create the view model
        LanguageViewModel langViewModel =
                new ViewModelProvider(this).get(LanguageViewModel.class);
        
        // Get the binding and the root
        binding = FragmentLanguageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // add listener to the start button
        binding.startButton.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.navigation_lang_question);
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