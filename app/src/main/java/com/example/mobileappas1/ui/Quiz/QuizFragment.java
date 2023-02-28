package com.example.mobileappas1.ui.Quiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mobileappas1.R;
import com.example.mobileappas1.databinding.FragmentQuizBinding;

//import com.example.mobileappas1.databinding.frag;

public class QuizFragment extends Fragment {

    private FragmentQuizBinding binding;

    private boolean maths, history, geography;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QuizViewModel quizViewModel =
                new ViewModelProvider(this).get(QuizViewModel.class);

        binding = FragmentQuizBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.mathToggle.setOnClickListener(view -> {
            disableAll();
            binding.mathToggle.setChecked(true);
            maths = true;
        });
        binding.historyToggle.setOnClickListener(view -> {
            disableAll();
            binding.historyToggle.setChecked(true);
            history = true;
        });
        binding.geographyToggle.setOnClickListener(view -> {
            disableAll();
            binding.geographyToggle.setChecked(true);
            geography = true;
        });

        binding.startquizButton.setOnClickListener(view -> {
            if(!maths && !history && !geography)
            {
                Toast.makeText(getContext(), R.string.no_quiz_selected, Toast.LENGTH_SHORT).show();
                return;
            }
            // if maths then set as 1, then if geography set as 2 then finally set to 3 if history
            int quizID = maths ? 1 : history ? 2 : 3;
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("quizID", quizID);
            editor.apply();

            Navigation.findNavController(view).navigate(R.id.navigation_quiz_question);
        });

        return root;
    }

    public void disableAll()
    {
        binding.mathToggle.setChecked(false);
        binding.historyToggle.setChecked(false);
        binding.geographyToggle.setChecked(false);
        maths = false;
        history = false;
        geography = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}