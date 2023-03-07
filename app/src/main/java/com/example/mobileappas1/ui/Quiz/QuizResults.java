package com.example.mobileappas1.ui.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizResults
{
    private ArrayList name = new ArrayList<>();
    private ArrayList date = new ArrayList<>();;
    private ArrayList<Integer> score = new ArrayList<>();;

    public ArrayList getName(){return name;}
    public void setName(ArrayList value){this.name = value;}

    public ArrayList getDate(){return date;}
    public void setDate(ArrayList value){this.date = value;}

    public ArrayList getScore(){return score;}
    public void setScore(ArrayList<Integer> value){this.score = value;}
}
