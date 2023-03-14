package com.example.mobileappas1.ui.Notes.newNote;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/*
 * this class is the view model which i did not end up using 
 */
public class NewNoteViewModel extends ViewModel {
    // Private variables
    private final MutableLiveData<String> mText;

    /*
     * Constructor
     */
    public NewNoteViewModel() {
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