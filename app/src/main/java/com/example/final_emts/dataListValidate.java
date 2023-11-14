package com.example.final_emts;

public class dataListValidate {

    private String title, description, key;
    private boolean accept;
    private boolean reject;

    public dataListValidate(String title, boolean accept, boolean reject) {
        this.key = key;
        this.title = title;
        this.accept = accept;
        this.reject = reject;
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

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public boolean isReject() {
        return reject;
    }

    public void setReject(boolean reject) {
        this.reject = reject;
    }

    public String getKey() {
        return key;
    }
}
