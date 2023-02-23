package com.example.mobileappas1.ui.Notes.newNote;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewNoteViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NewNoteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is new note fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}