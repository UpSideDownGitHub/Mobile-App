package com.example.mobileappas1.ui.Quiz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/*
 * this class is the view model which i did not end up using 
 */
public class QuizResultsViewModel extends ViewModel {
    // Private variables
    private final MutableLiveData<String> mText;

    /*
     * Constructor
     */
    public QuizResultsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    /*
     * return the text
     */
    public LiveData<String> getText() {
        return mText;
    }
}