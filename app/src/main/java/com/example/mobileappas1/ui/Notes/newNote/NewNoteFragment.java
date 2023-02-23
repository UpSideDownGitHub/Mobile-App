package com.example.mobileappas1.ui.Notes.newNote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobileappas1.databinding.FragmentNewNoteBinding;
import com.example.mobileappas1.ui.Notes.newNote.NewNoteViewModel;

import com.example.mobileappas1.databinding.FragmentNewNoteBinding;

public class NewNoteFragment extends Fragment {

    private FragmentNewNoteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewNoteViewModel newNoteViewModel =
                new ViewModelProvider(this).get(NewNoteViewModel.class);

        binding = FragmentNewNoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return getView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}