package com.example.mobileappas1.ui.Notes;

import java.util.ArrayList;

/*
 * holdsall of the user data for the save file
 */
public class User {
    // variables
    private String id;
    private String name;
    private ArrayList<Note> notes;

    // Getters & Setters
    public String getID() {
        return id;
    }

    public void setID(String value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> value) {
        this.notes = value;
    }
}
