package com.example.mobileappas1.ui.Quiz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuizResultsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public QuizResultsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}