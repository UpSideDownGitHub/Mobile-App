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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappas1.R;
import com.example.mobileappas1.databinding.FragmentQuizBinding;
import com.example.mobileappas1.ui.Notes.NotesData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//import com.example.mobileappas1.databinding.frag;

public class QuizFragment extends Fragment {

    private FragmentQuizBinding binding;
    QuizAdapter adapter;
    QuizResults quizResults = new QuizResults();

    FileOutputStream outputStream;

    private boolean maths, history, geography;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QuizViewModel quizViewModel =
                new ViewModelProvider(this).get(QuizViewModel.class);

        binding = FragmentQuizBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // show all of the scores on the high score list
        adapter = new QuizAdapter(getActivity(), getContext(), new ArrayList<>());
        RecyclerView recyclerView = (RecyclerView) binding.quizRecyclerview;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        String path = getContext().getFilesDir().getAbsolutePath() + "/" + "savedQuizResults.txt";
        File file = new File(path);
        if (!file.exists())
        {
            Log.i("DEBUG", "Does not exist");
            // create the Json file from the data
            Gson gson = new Gson();
            String json = gson.toJson(quizResults);

            // write the file
            try {
                outputStream = getContext().openFileOutput("savedQuizResults.txt", getContext().MODE_PRIVATE);
                outputStream.write(json.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // read the file
        FileInputStream fis = null;
        try {
            fis = getContext().openFileInput("savedQuizResults.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while (true) {
            try {
                if (!((line = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sb.append(line);
        }
        String json2 = sb.toString();
        Gson gson2 = new Gson();
        QuizResults data = gson2.fromJson(json2, QuizResults.class);
        // data is the data that has been read
        quizResults = data;

        // UPDATE THE LIST OF ITEMS BEING SHOWN

        // load all the new data into the adapters
        adapter.clearList();
        // take the data and read the file
        List<String> names = quizResults.getName();
        for (int i = 0; i < names.size(); i++) {
            adapter.addValue(names.get(i));
            Log.i("PLEASE ALLOW IT TO WORK", names.get(i));
        }
        adapter.update();

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