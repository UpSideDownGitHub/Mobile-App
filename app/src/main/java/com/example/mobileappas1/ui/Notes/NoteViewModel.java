package com.example.mobileappas1.ui.Notes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/*
 * this class is the view model which i did not end up using 
 */
public class NoteViewModel extends ViewModel {
    // Private variables
    private final MutableLiveData<String> mText;

    /*
     * Constructor
     */
    public NoteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    /*
     * return the text
     */
    public LiveData<String> getText() {
        return mText;
    }
}