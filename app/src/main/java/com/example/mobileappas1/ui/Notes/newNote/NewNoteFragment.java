package com.example.mobileappas1.ui.Notes.newNote;

import static java.time.format.DateTimeFormatter.ISO_DATE;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
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
import java.time.LocalDateTime;
import java.util.ArrayList;

public class NewNoteFragment extends Fragment {

    private FragmentNewNoteBinding binding;
    NotesData notesData = new NotesData();
    NotesAdapter adapter;
    ArrayList notesForList = new ArrayList<>();
    public int playerID;
    FileOutputStream outputStream;

    /*
     * this method will run when the view is created and will initilaise all
     * elemtents as well as setting all of the listeners
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Create the view model 
        NewNoteViewModel newNoteViewModel =
                new ViewModelProvider(this).get(NewNoteViewModel.class);
        // Get the binding, root, and adapter
        binding = FragmentNewNoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        adapter = new NotesAdapter(getActivity(), this.getContext(), notesForList);

        // add note button pressed listener
        binding.addnoteButton.setOnClickListener( view -> addNoteButtonPressed(view));
        // cancel note making button listerner
        binding.cancelnoteButton.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.navigation_notes);
        });

        // get the player ID
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        playerID = sharedPref.getInt("playerID", 0);

        // set the title of the note to the current date and name of the player
        String[] usernames = getResources().getStringArray(R.array.usernames);

        // if can then add the date to the title of the note
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.titleEdittext.setText(usernames[playerID] + " " + LocalDateTime.now().format(ISO_DATE));
        }

        // return root
        return root;
    }

    /*
     * this will handle adding a new note
     */
    public void addNoteButtonPressed(View view)
    {
        // get the title and the contents of the note to be created
        String title = binding.titleEdittext.getText().toString();
        String contents = binding.contentsEdittext.getText().toString();

        // read the file
        readFile();

        // check if title already exists
        ArrayList<Note> savedData =  notesData.getUsers().getUser().get(playerID).getNotes();
        for (int i = 0; i < savedData.size(); i++)
        {
            // if title exists
            if (savedData.get(i).getTitle().equals(title))
            {
                // show error and stop
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

        // write new data to the file
        writeFile();

        // go to the main notes screen
        Navigation.findNavController(view).navigate(R.id.navigation_notes);
    }

    /*
     * handles writing the file to the correct place
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
     * handles reading the file and converting its data to a class
     */
    public void readFile()
    {
        // open the file
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
        // read the file line by line
        while (true) {
            try {
                if (!((line = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sb.append(line);
        }
        // convert read file to class format
        String json2 = sb.toString();
        Gson gson2 = new Gson();
        NotesData data = gson2.fromJson(json2, NotesData.class);

        // data is the data that has been read
        notesData = data;
    }

    /*
     * handle properly destroying the view 
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}