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

/*
 * The SelectionActivity will handle the main elements of the game, that being
 * the navigation bar and also the changing of the title of the application
 * when switching to a new activity.
 */
public class SelectionActivity extends AppCompatActivity {
    // Private variables
    private ActivitySelectionBinding binding;
    
    // Public variables
    public int playerID;
    public String playerName;

    /*
     * This function is called when the activity is created and will 
     * initialize all of the elements that are needed as well as this it also
     * sets up the nav controller and the listener to change the title
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // get the player name and id which was sent from the MainActivity
        int i = getIntent().getExtras().getInt("playerID");
        playerName = getIntent().getExtras().getString("Player Name");

        // save the ID to the preferences to be accessed everywhere else in the code
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("playerID", i);
        editor.apply();

        // set the current view to the root of the scene
        binding = ActivitySelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // set up the navigation bar at the top
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // send the IDs of all of the different screens that can be navigated to
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
        // finalize setting up the nav controller 
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_selection);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // attach a listener to the nav controller to change the title of the screen to the
        // new destination
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            String title = "";
            // change the title based on the navigation date
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

            // set the title to to the new title, which will be the name of the destination plus
            // the name of the player 
            getSupportActionBar().setTitle(title);
        });
        // set the default title of the application to notes at that is the first screen the player
        // will see
        getSupportActionBar().setTitle("Notes - " + playerName);
    }
}