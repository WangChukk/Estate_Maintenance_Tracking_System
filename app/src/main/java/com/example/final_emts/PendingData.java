package com.example.final_emts;

public class PendingData {
    private String key;
    private String title;
    private String description;
    private String location;

    public PendingData() {
        // Required by Firebase
    }

    public PendingData(String key, String title, String description, String location) {
        this.key = key;
        this.title = title;
        this.description = description;
        this.location = location;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
