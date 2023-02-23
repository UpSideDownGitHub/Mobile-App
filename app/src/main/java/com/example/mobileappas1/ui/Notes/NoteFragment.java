package com.example.mobileappas1.ui.Notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappas1.R;
import com.example.mobileappas1.databinding.FragmentNoteBinding;
import com.example.mobileappas1.ui.Calc.CalcAdapter;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NoteFragment extends Fragment {

    private FragmentNoteBinding binding;

    FileOutputStream outputStream;

    NotesData notesData = new NotesData();

    CalcAdapter adapter;
    ArrayList notesForList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NoteViewModel noteViewModel =
                new ViewModelProvider(this).get(NoteViewModel.class);

        binding = FragmentNoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        createFile();
        if (!isFilePresent(getContext(), "savedNotes.txt")) {
            Log.i("DEBUG", "NO FILE");
            createFile();
        }

        readFile();

        RecyclerView recyclerView = (RecyclerView) binding.notesRecyclerview;

        // Setting the layout as linear
        // layout for vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        // Sending reference and data to Adapter
        adapter = new CalcAdapter(this.getContext(), notesForList);

        // Setting Adapter to RecyclerView
        recyclerView.setAdapter(adapter);


        // check for button press
        binding.notesActionbutton.setOnClickListener( view -> addNewClicked(view));


        return root;
    }

    public void addNewClicked(View view)
    {
        Navigation.findNavController(view).navigate(R.id.navigation_new_notes);
    }

    public boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }
    public void createFile()
    {
        // note
        Note note = new Note();
        note.setID("1");
        note.setTitle("Example Note 1");
        note.setContents("Put the contents of the note here");
        Note note2 = new Note();
        note2.setID("1");
        note2.setTitle("Example Note 2");
        note2.setContents("Put the contents of the note here");
        Note note3 = new Note();
        note3.setID("1");
        note3.setTitle("Example Note 3");
        note3.setContents("Put the contents of the note here");
        Note note4 = new Note();
        note4.setID("1");
        note4.setTitle("Example Note 4");
        note4.setContents("Put the contents of the note here");
        Note note5 = new Note();
        note5.setID("1");
        note5.setTitle("Example Note 5");
        note5.setContents("Put the contents of the note here");
        Note note6 = new Note();
        note6.setID("1");
        note6.setTitle("Example Note 6");
        note6.setContents("Put the contents of the note here");
        Note note7 = new Note();
        note7.setID("1");
        note7.setTitle("Example Note 7");
        note7.setContents("Put the contents of the note here");
        Note note8 = new Note();
        note8.setID("1");
        note8.setTitle("Example Note 8");
        note8.setContents("Put the contents of the note here");
        Note note9 = new Note();
        note9.setID("1");
        note9.setTitle("Example Note 9");
        note9.setContents("Put the contents of the note here");
        Note note10 = new Note();
        note10.setID("1");
        note10.setTitle("Example Note 10");
        note10.setContents("Put the contents of the note here");

        // user
        User user = new User();
        user.setID("1");
        user.setName("User 1");
        User user2 = new User();
        user2.setID("2");
        user2.setName("User 2");
        User user3 = new User();
        user3.setID("3");
        user3.setName("User 3");
        User user4 = new User();
        user4.setID("4");
        user4.setName("User 4");
        User user5 = new User();
        user5.setID("5");
        user5.setName("User 5");
        User user6 = new User();
        user6.setID("6");
        user6.setName("User 6");
        User user7 = new User();
        user7.setID("7");
        user7.setName("User 7");
        User user8 = new User();
        user8.setID("8");
        user8.setName("User 8");
        User user9 = new User();
        user9.setID("9");
        user9.setName("User 9");
        User user10 = new User();
        user10.setID("10");
        user10.setName("User 10");
        user.setNotes(new Note[]{note});
        user2.setNotes(new Note[]{note2});
        user3.setNotes(new Note[]{note3});
        user4.setNotes(new Note[]{note4});
        user5.setNotes(new Note[]{note5});
        user6.setNotes(new Note[]{note6});
        user7.setNotes(new Note[]{note7});
        user8.setNotes(new Note[]{note8});
        user9.setNotes(new Note[]{note9});
        user10.setNotes(new Note[]{note10});


        // users
        Users users = new Users();
        users.setUser(new User[]{user,user2,user3,user4,user5,user6,user7,user8,user9,user10});

        notesData.setUsers(users);

        writeFile();
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


        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int playerID = sharedPref.getInt("playerID", 0);

        notesForList.clear();
        Note[] savedData =  notesData.getUsers().getUser()[playerID].getNotes();
        for (int i = 0; i < savedData.length; i++)
        {
            notesForList.add(0, savedData[i].getTitle());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}