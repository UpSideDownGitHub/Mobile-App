package com.example.mobileappas1.ui.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizResults
{
    private List<String> name = new ArrayList<>();
    private List<String> date = new ArrayList<>();;
    private List<Integer> score = new ArrayList<>();;

    public List<String> getName(){return name;}
    public void setName(List<String> value){this.name = value;}

    public List<String> getDate(){return date;}
    public void setDate(List<String> value){this.date = value;}

    public List<Integer> getScore(){return score;}
    public void setScore(List<Integer> value){this.score = value;}
}
