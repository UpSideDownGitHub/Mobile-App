package com.example.mobileappas1.ui.Notes.editNote;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobileappas1.R;
import com.example.mobileappas1.databinding.FragmentEditNoteBinding;
import com.example.mobileappas1.databinding.FragmentLanguageBinding;
import com.example.mobileappas1.ui.Calc.CalcViewModel;
import com.example.mobileappas1.ui.Notes.Note;
import com.example.mobileappas1.ui.Notes.NotesData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class EditNoteFragment extends Fragment {

    private FragmentEditNoteBinding binding;

    private int playerID, noteID;
    NotesData notesData = new NotesData();
    FileOutputStream outputStream;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EditNoteViewModel editNoteViewModel =
                new ViewModelProvider(this).get(EditNoteViewModel.class);

        binding = FragmentEditNoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        noteID = sharedPref.getInt("currentNoteID", 0);
        playerID = sharedPref.getInt("playerID", 0);
        //Log.i("DEBUG", "ID: " + ID);

        readFile();

        String title = notesData.getUsers().getUser().get(playerID).getNotes().get(noteID).getTitle();
        String contents = notesData.getUsers().getUser().get(playerID).getNotes().get(noteID).getContents();
        binding.edittitleEdittext.setText(title);
        binding.editcontentsEdittext.setText(contents);

        binding.editnoteButton.setOnClickListener( view -> saveEditedNote(view));

        return root;
    }

    public void saveEditedNote(View view)
    {
        String title = binding.edittitleEdittext.getText().toString();
        String contents = binding.editcontentsEdittext.getText().toString();

        // read the file
        FileInputStream fis = null;
        try {
            fis = getContext().openFileInput("savedNotes.txt");
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
        NotesData data = gson2.fromJson(json2, NotesData.class);

        // data is the data that has been read
        notesData = data;

        // get the player ID
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        playerID = sharedPref.getInt("playerID", 0);

        // check if title already exists
        ArrayList<Note> savedData =  notesData.getUsers().getUser().get(playerID).getNotes();
        for (int i = 0; i < savedData.size(); i++)
        {
            if (i == noteID)
                continue;
            if (savedData.get(i).getTitle().equals(title))
            {
                Toast.makeText(
                        getContext(),
                        R.string.same_note_title,
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // save the new edited note
        // Update Title
        notesData.getUsers().getUser().get(playerID).getNotes().get(noteID).setTitle(title);
        // Update Contents
        notesData.getUsers().getUser().get(playerID).getNotes().get(noteID).setContents(contents);

        writeFile();

        Navigation.findNavController(view).navigate(R.id.navigation_notes);
    }

    public void writeFile()
    {
        // create the Json file from the data
        Gson gson = new Gson();
        String json = gson.toJson(notesData);

        // write the file
        try {
            outputStream = getContext().openFileOutput("savedNotes.txt", getContext().MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void readFile()
    {
        // read the file
        FileInputStream fis = null;
        try {
            fis = getContext().openFileInput("savedNotes.txt");
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
        NotesData data = gson2.fromJson(json2, NotesData.class);
        // data is the data that has been read
        notesData = data;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}