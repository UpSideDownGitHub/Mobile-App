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

/*
 * this class is the main class for thr quiz and will handle the main screen
 * of the quiz activity
 */
public class QuizFragment extends Fragment {

    // Private Variables
    private FragmentQuizBinding binding;
    private QuizAdapter adapter;
    private QuizResults quizResults = new QuizResults();
    private FileOutputStream outputStream;
    private boolean maths, history, geography;
    private int quizID;

    /*
     * this will handle initializing the view and then load all of the relevant data from the file
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // get the view model for this class
        QuizViewModel quizViewModel =
                new ViewModelProvider(this).get(QuizViewModel.class);
        
        // set the binding for the class and get the root 
        binding = FragmentQuizBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // show all of the scores on the high score list
        adapter = new QuizAdapter(getActivity(), getContext(), new ArrayList<>());
        RecyclerView recyclerView = (RecyclerView) binding.quizRecyclerview;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        
        // Load the data from the saved quiz results 
        String path = getContext().getFilesDir().getAbsolutePath() + "/" + "savedQuizResults.txt";
        File file = new File(path);
        // if there is not a file if the given location
        if (!file.exists())
        {
            // create the Json file from the data
            Gson gson = new Gson();
            String json = gson.toJson(quizResults);

            // write the file to storage
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
        // read each line of the file and add it to the StringBuilder
        while (true) {
            try {
                if (!((line = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sb.append(line);
        }
        // convert read data to the class form
        String json2 = sb.toString();
        Gson gson2 = new Gson();
        QuizResults data = gson2.fromJson(json2, QuizResults.class);
        // data is the data that has been read
        quizResults = data;

        // add listeners to all of toggles to the user can select there quiz type
        binding.mathToggle.setOnClickListener(view -> {
            disableAll();
            quizID = 1;
            binding.mathToggle.setChecked(true);
            maths = true;
            updateAdapterView();
        });
        binding.historyToggle.setOnClickListener(view -> {
            disableAll();
            quizID = 2;
            binding.historyToggle.setChecked(true);
            history = true;
            updateAdapterView();
        });
        binding.geographyToggle.setOnClickListener(view -> {
            disableAll();
            quizID = 3;
            binding.geographyToggle.setChecked(true);
            geography = true;
            updateAdapterView();
        });
        
        // set bindinig to the start button so when clicked the quiz will start
        binding.startquizButton.setOnClickListener(view -> {
            // if there is no quiz selected
            if(!maths && !history && !geography)
            {
                // show error message
                Toast.makeText(getContext(), R.string.no_quiz_selected, Toast.LENGTH_SHORT).show();
                return;
            }
            // if maths then set as 1, then if geography set as 2 then finally set to 3 if history
            int quizID = maths ? 1 : history ? 2 : 3;
            // save the current quiz to shared preferneces so the question fragment can show
            // the correct questions 
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("quizID", quizID);
            editor.apply();
            
            // move to the quiz fragment
            Navigation.findNavController(view).navigate(R.id.navigation_quiz_question);
        });
        // return the root
        return root;
    }

    /*
     * disable all of the toggles and set the selcted to nothing
     */
    public void disableAll()
    {
        binding.mathToggle.setChecked(false);
        binding.historyToggle.setChecked(false);
        binding.geographyToggle.setChecked(false);
        maths = false;
        history = false;
        geography = false;
        quizID = 0;
    }

    /*
     * update the recycler view to show the new highscore list for the currently
     * selected quiz
     */
    public void updateAdapterView()
    {
        // clear the list of items in the current adapter
        adapter.clearList();
        // take the data from the read file
        List<String> names = quizResults.getName();
        List<String> dates = quizResults.getDate();
        List<Integer> scores = quizResults.getScore();
        List<Integer> types = quizResults.getType();
        // for each peice of data if it was achived with the current
        // quizID then add it to the current recycler view
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i) == quizID)
                adapter.addValue(names.get(i), scores.get(i).toString(), dates.get(i));
        }
        // update the adapter to show all the new data
        adapter.update();
    }

    /*
     * handle the destroying of the view
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}