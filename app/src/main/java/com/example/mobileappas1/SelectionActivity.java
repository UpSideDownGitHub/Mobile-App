package com.example.mobileappas1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mobileappas1.ui.Notes.NoteFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobileappas1.databinding.ActivitySelectionBinding;

public class SelectionActivity extends AppCompatActivity {
    private ActivitySelectionBinding binding;

    public int playerID;
    public String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int i = getIntent().getExtras().getInt("playerID");
        playerName = getIntent().getExtras().getString("Player Name");

        // save the ID to the preferences to be accessed everywhere else
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("playerID", i);
        editor.apply();


        binding = ActivitySelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_notes,
                R.id.navigation_quiz,
                R.id.navigation_quiz_question,
                R.id.navigation_quiz_results,
                R.id.navigation_calc,
                R.id.navigation_dice,
                R.id.navigation_lang,
                R.id.navigation_lang_question,
                R.id.navigation_lang_result,
                R.id.navigation_new_notes,
                R.id.navigation_edit_notes)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_selection);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            //Log.w("DEBUG", "A");
            String title = "";

            if (destination.getId() == R.id.navigation_notes)
                title = "Notes - " + playerName;
            else if (destination.getId() == R.id.navigation_new_notes)
                title = "New Note - " + playerName;
            else if (destination.getId() == R.id.navigation_edit_notes)
                title = "Edit Note - " + playerName;
            else if (destination.getId() == R.id.navigation_quiz)
                title = "Quiz - " + playerName;
            else if (destination.getId() == R.id.navigation_quiz_question)
                title = "Questions - " + playerName;
            else if (destination.getId() == R.id.navigation_quiz_results)
                title = "Results - " + playerName;
            else if (destination.getId() == R.id.navigation_lang)
                title = "Language - " + playerName;
            else if (destination.getId() == R.id.navigation_lang_question)
                title = "Questions - " + playerName;
            else if (destination.getId() == R.id.navigation_lang_result)
                title = "Results - " + playerName;
            else if (destination.getId() == R.id.navigation_dice)
                title = "Dice - " + playerName;
            else if (destination.getId() == R.id.navigation_calc)
                title = "Calculator - " + playerName;

            getSupportActionBar().setTitle(title);
        });
        getSupportActionBar().setTitle("Notes - " + playerName);
    }
}