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

/*
 * Fragment for the edit note screen
 */
public class EditNoteFragment extends Fragment {

    // Private varaibles
    private FragmentEditNoteBinding binding;
    private int playerID, noteID;
    private NotesData notesData = new NotesData();
    private FileOutputStream outputStream;

    /*
     * this method will run when the view is created and will initilaise all
     * elemtents as well as setting all of the listeners
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Create the view model    
        EditNoteViewModel editNoteViewModel =
                new ViewModelProvider(this).get(EditNoteViewModel.class);

        // Get the binding and the root
        binding = FragmentEditNoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        
        // load the current note and the player ID from shared preferences
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        noteID = sharedPref.getInt("currentNoteID", 0);
        playerID = sharedPref.getInt("playerID", 0);

        // read the file to load all of the date from it
        readFile();

        // get the title and contents from the read title
        String title = notesData.getUsers().getUser().get(playerID).getNotes().get(noteID).getTitle();
        String contents = notesData.getUsers().getUser().get(playerID).getNotes().get(noteID).getContents();
        binding.edittitleEdittext.setText(title);
        binding.editcontentsEdittext.setText(contents);

        // save note button listener
        binding.editnoteButton.setOnClickListener( view -> saveEditedNote(view));
        // delte note button listener
        binding.deletenoteButton.setOnClickListener(view -> deleteNote(view));

        // retunr root
        return root;
    }

    /*
     * deletes the current note
     */
    public  void deleteNote(View view)
    {
        // read the file
        readFile();

        // remove the selected note
        ArrayList notes = notesData.getUsers().getUser().get(playerID).getNotes();
        notes.remove(noteID);
        notesData.getUsers().getUser().get(playerID).setNotes(notes);

        // write the new data to the file
        writeFile();

        // go to the main notes screen
        Navigation.findNavController(view).navigate(R.id.navigation_notes);
    }

    /*
     * updates the editied note to show the new data
     */
    public void saveEditedNote(View view)
    {
        // get the title and contents of the new note
        String title = binding.edittitleEdittext.getText().toString();
        String contents = binding.editcontentsEdittext.getText().toString();

        // read the file
        readFile();

        // check if title already exists
        ArrayList<Note> savedData =  notesData.getUsers().getUser().get(playerID).getNotes();
        for (int i = 0; i < savedData.size(); i++)
        {
            // if the current note is the note being checked then move on
            if (i == noteID)
                continue;
            // if the title is the same as another title 
            if (savedData.get(i).getTitle().equals(title))
            {
                // show error message and stop saving
                Toast.makeText(
                        getContext(),
                        R.string.same_note_title,
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }

        
        // Update Title
        notesData.getUsers().getUser().get(playerID).getNotes().get(noteID).setTitle(title);
        // Update Contents
        notesData.getUsers().getUser().get(playerID).getNotes().get(noteID).setContents(contents);
        // save the new edited note to file
        writeFile();

        // navigate to the main notes screen 
        Navigation.findNavController(view).navigate(R.id.navigation_notes);
    }

    /*
     * will handle writing the file to the correct place
     */
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

    /*
     * reads the file and converts it to a class of data
     */
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
        // get all the data in the file
        while (true) {
            try {
                if (!((line = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sb.append(line);
        }
        // convert the read data to its class format
        String json2 = sb.toString();
        Gson gson2 = new Gson();
        NotesData data = gson2.fromJson(json2, NotesData.class);
        // data is the data that has been read
        notesData = data;
    }

    /*
     * handles destroying the view correctly
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}