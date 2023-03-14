package com.example.mobileappas1.ui.Notes.editNote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/*
 * this class is the view model which i did not end up using 
 */
public class EditNoteViewModel extends ViewModel {
    // Private variables
    private final MutableLiveData<String> mText;

    /*
     * Constructor
     */
    public EditNoteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is new note fragment");
    }

    /*
     * return the text
     */
    public LiveData<String> getText() {
        return mText;
    }
}