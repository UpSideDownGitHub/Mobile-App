package com.example.mobileappas1.ui.Language;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LanguageViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public LanguageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}