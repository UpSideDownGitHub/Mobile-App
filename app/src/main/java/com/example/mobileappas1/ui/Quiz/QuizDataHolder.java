package com.example.mobileappas1.ui.Quiz;

/*
 * this class will be used to hold the information about each item in the 
 * quiz adapter
 */
public class QuizDataHolder
{
    // Private variables
    private String name, score, date;

    /*
     * Constructor will initialize the class
     */
    QuizDataHolder(String name, String score, String date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }

    /*
     * returns the name
     */
    public String getName() {
        return name;
    }

    /*
     * returns the score
     */
    public String getScore() {
        return score;
    }

    /*
     * returns the date
     */
    public String getDate(){
        return date;
    }
}
