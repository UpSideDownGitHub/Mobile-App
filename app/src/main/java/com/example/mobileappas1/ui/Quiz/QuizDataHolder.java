package com.example.mobileappas1.ui.Quiz;

public class QuizDataHolder
{
    private String name, score, date;

    QuizDataHolder(String name, String score, String date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public String getDate(){
        return date;
    }
}
