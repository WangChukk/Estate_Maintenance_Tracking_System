package com.example.final_emts;

public class CompleteItem {
    private String title;
    private String description;

    public CompleteItem() {
        // Required empty constructor for Firebase Realtime Database
    }

    public CompleteItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Getters and setters for the fields

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
