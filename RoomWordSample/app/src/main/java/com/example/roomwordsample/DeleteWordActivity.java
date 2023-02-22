package com.example.roomwordsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class DeleteWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_word);
        //mEditWordView = findViewById(R.id.edit_word);

        final Button buttonYes = findViewById(R.id.button_yes);
        final Button buttonNo = findViewById(R.id.button_no);


        buttonYes.setOnClickListener(view -> {
            Intent replyIntent = new Intent();

            replyIntent.putExtra(EXTRA_REPLY, "YES");
            setResult(RESULT_OK, replyIntent);

            finish();
        });

        buttonNo.setOnClickListener(view -> {
            Intent replyIntent = new Intent();

            replyIntent.putExtra(EXTRA_REPLY, "NO");
            setResult(RESULT_CANCELED, replyIntent);

            finish();
        });
    }
}