package com.example.mobileappas1.ui.Notes;

/*
 * data for the notes to be saved properly to the file
 */
public class Note {
    // Variables
    private String id;
    private String title;
    private String contents;

    // Getters & Setters
    public String getID() {
        return id;
    }

    public void setID(String value) {
        this.id = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String value) {
        this.contents = value;
    }
}
