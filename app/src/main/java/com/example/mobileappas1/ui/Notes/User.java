package com.example.mobileappas1.ui.Notes;

import java.util.ArrayList;

public class User {
    private String id;
    private String name;
    private ArrayList<Note> notes;

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
