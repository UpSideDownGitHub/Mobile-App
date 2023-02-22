package com.example.roomwordsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private WordViewModel mWordViewModel;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int DELETE_ALL_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(new WordListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getmAllWords().observe(this, words -> {
            adapter.submitList(words);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener( view -> {
            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener( view -> {
            Intent intent = new Intent(MainActivity.this, DeleteWordActivity.class);
            startActivityForResult(intent, DELETE_ALL_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DELETE_ALL_REQUEST_CODE && resultCode == RESULT_OK)
        {
            // delete all of the words
            mWordViewModel.deleteAll();
            Toast.makeText(
                    getApplicationContext(),
                    R.string.database_deleted,
                    Toast.LENGTH_LONG).show();
        }
        else if (requestCode == DELETE_ALL_REQUEST_CODE && resultCode == RESULT_CANCELED)
        {
            // Do Nothing as action canceled
            Toast.makeText(
                    getApplicationContext(),
                    R.string.delete_cancelled,
                    Toast.LENGTH_LONG).show();
        }

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            mWordViewModel.insert(word);
            Toast.makeText(
                    getApplicationContext(),
                    R.string.word_added,
                    Toast.LENGTH_LONG).show();

        } else if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE){
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}

