package com.example.mobileappas1.ui.Quiz;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobileappas1.R;
import com.example.mobileappas1.databinding.FragmentQuizBinding;
import com.example.mobileappas1.databinding.FragmentQuizQuestionBinding;

public class QuizQuestionFragment extends Fragment {

    private FragmentQuizQuestionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QuizViewModel quizViewModel =
                new ViewModelProvider(this).get(QuizViewModel.class);

        binding = FragmentQuizQuestionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}