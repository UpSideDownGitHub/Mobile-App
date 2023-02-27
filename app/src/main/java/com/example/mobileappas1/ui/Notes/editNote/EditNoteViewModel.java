package com.example.mobileappas1.ui.Notes.editNote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditNoteViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EditNoteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is new note fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}