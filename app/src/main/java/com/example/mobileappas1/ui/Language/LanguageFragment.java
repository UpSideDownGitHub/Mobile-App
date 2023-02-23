package com.example.mobileappas1.ui.Language;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobileappas1.R;
//import com.example.mobileappas1.databinding.FragmentNotificationsBinding;
import com.example.mobileappas1.ui.Calc.CalcViewModel;

public class LanguageFragment extends Fragment {

    //private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalcViewModel calcViewModel =
                new ViewModelProvider(this).get(CalcViewModel.class);

        //binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();

        //final TextView textView = binding.textNotifications;
        //calcViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return getView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }

}