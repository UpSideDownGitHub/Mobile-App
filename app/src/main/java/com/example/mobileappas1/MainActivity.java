package com.example.mobileappas1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public String[] usernames = {"one", "two", "three", "four", "five",
                                 "six", "seven", "eight", "nine", "ten"};
    public String[] passwords = {"one", "two", "three", "four", "five",
                                 "six", "seven", "eight", "nine", "ten"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener( view -> checkLogin());
    }

    public void checkLogin()
    {
        EditText username = findViewById(R.id.username_edittext);
        EditText password = findViewById(R.id.password_edittext);

        for (int i = 0; i < usernames.length; i++)
        {
            if (username.getText().toString().equals(usernames[i]))
            {
                if (password.getText().toString().equals(passwords[i]))
                {
                    // both are correct so move to next window
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.welcome,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                //password incorrect
                Toast.makeText(
                        getApplicationContext(),
                        R.string.password_incorrect,
                        Toast.LENGTH_LONG).show();
                return;
            }
        }
        // Username Incorrect
        Toast.makeText(
                getApplicationContext(),
                R.string.username_incorrect,
                Toast.LENGTH_LONG).show();
    }
}