package com.example.final_emts;

public class MaintenanceTeamData {
    private String title;
    private String description;
    private String date;
    private String time;
    private String key;
    private String location;
    private String contact;
    private String source; // Add the source field

    public MaintenanceTeamData() {
        // Default constructor required for Firebase
    }

    public MaintenanceTeamData(String title, String description, String date, String time, String location, String contact, String source) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.location = location;
        this.contact = contact;
        this.source = source; // Initialize the source field
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
