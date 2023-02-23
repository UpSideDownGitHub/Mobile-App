package com.example.mobileappas1.ui.Notes;

public class User {
    private String id;
    private String name;
    private Note[] notes;

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

    public Note[] getNotes() {
        return notes;
    }

    public void setNotes(Note[] value) {
        this.notes = value;
    }
}
