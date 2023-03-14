package com.example.mobileappas1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 * This is the first class of the game and it will handle the user login
 * as well as passing the login information to the SelectionActivity class
 */
public class MainActivity extends AppCompatActivity {
    // public Variables
    public String[] usernames;
    public String[] passwords = {"one", "two", "three", "four", "five",
                                 "six", "seven", "eight", "nine", "ten"};

    /*
    * the on create function handles setting up the script and loading all of the necessary data 
    */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // show the main login screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // change the title of the application and hide the title bar
        getSupportActionBar().setTitle("");
        getSupportActionBar().hide();

        // load the usernames from there array
        usernames = getResources().getStringArray(R.array.usernames);

        // set a listener to the login button to call the checkLogin() function
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener( view -> checkLogin());
    }

    /*
     * this method will check if the entered username and password match
     * and if they do will send the ID and name to the SelectionActivity so
     * they can be accessed through the rest of the application
     */
    public void checkLogin()
    {
        // get the username and password from there text views
        EditText username = findViewById(R.id.username_edittext);
        EditText password = findViewById(R.id.password_edittext);

        // for all of the possible usernames
        for (int i = 0; i < usernames.length; i++)
        {
            // if the username is equal to the current username
            if (username.getText().toString().equals(usernames[i]))
            {
                // if the password matches the expected password for this username
                if (password.getText().toString().equals(passwords[i]))
                {
                    // show welcome text
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.welcome,
                            Toast.LENGTH_SHORT).show();

                    // add intent to the go to the SelectionActivity and add the playerID and
                    // the Player Name to it.
                    Intent intent = new Intent(MainActivity.this,SelectionActivity.class);
                    intent.putExtra("playerID", i);
                    intent.putExtra("Player Name", usernames[i]);
                    startActivity(intent);
                    return;
                }

                // show password incorrect text
                Toast.makeText(
                        getApplicationContext(),
                        R.string.password_incorrect,
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }
        // show username Incorrect
        Toast.makeText(
                getApplicationContext(),
                R.string.username_incorrect,
                Toast.LENGTH_SHORT).show();
    }
}