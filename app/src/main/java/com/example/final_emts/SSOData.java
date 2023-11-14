package com.example.final_emts;

public class SSOData {
    private String title, key;
    private String description;

    public SSOData() {
        // Default constructor required for Firebase
    }

    public SSOData(String title, String key, String description) {
        this.title = title;
        this.key = key;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }
}
