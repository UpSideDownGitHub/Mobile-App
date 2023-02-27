package com.example.mobileappas1.ui.Notes.newNote;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.mobileappas1.R;
import com.example.mobileappas1.databinding.FragmentNewNoteBinding;
import com.example.mobileappas1.ui.Notes.Note;
import com.example.mobileappas1.ui.Notes.NotesAdapter;
import com.example.mobileappas1.ui.Notes.NotesData;
import com.example.mobileappas1.ui.Notes.newNote.NewNoteViewModel;

import com.example.mobileappas1.databinding.FragmentNewNoteBinding;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NewNoteFragment extends Fragment {

    private FragmentNewNoteBinding binding;
    NotesData notesData = new NotesData();
    NotesAdapter adapter;
    ArrayList notesForList = new ArrayList<>();
    public int playerID;
    FileOutputStream outputStream;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewNoteViewModel newNoteViewModel =
                new ViewModelProvider(this).get(NewNoteViewModel.class);
        binding = FragmentNewNoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        adapter = new NotesAdapter(this.getContext(), notesForList);
        binding.addnoteButton.setOnClickListener( view -> addNoteButtonPressed(view));
        return root;
    }

    public void addNoteButtonPressed(View view)
    {
        String title = binding.titleEdittext.getText().toString();
        String contents = binding.contentsEdittext.getText().toString();

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
            if (savedData.get(i).getTitle().equals(title))
            {
                Toast.makeText(
                        getContext(),
                        R.string.same_note_title,
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // save the new data
        Note newNote = new Note();
        newNote.setID(Integer.toString(notesData.getUsers().getUser().get(playerID).getNotes().size() + 1));
        newNote.setTitle(title);
        newNote.setContents(contents);
        notesData.getUsers().getUser().get(playerID).getNotes().add(newNote);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}