package com.example.final_emts;

public class recordsData {
    private String title;
    private String description;
    private String date;
    private String location;
    private String contact;
    private String time;

    public recordsData() {
        // Default constructor required for Firebase
    }

    public recordsData(String title, String description, String date, String location, String contact, String time) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.contact = contact;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
