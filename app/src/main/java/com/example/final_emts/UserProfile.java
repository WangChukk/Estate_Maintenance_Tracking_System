package com.example.final_emts;

public class UserProfile {
    private String name;
    private String department;
    private String stdID;
    private String stdEmail;
    private String userContact;
    private String userGender;
    private String profileImageURL; // Add the profileImageURL field

    // Empty constructor for Firebase
    public UserProfile() {
    }

    public UserProfile(String name, String department, String stdID, String stdEmail, String userContact, String userGender, String profileImageURL) {
        this.name = name;
        this.department = department;
        this.stdID = stdID;
        this.stdEmail = stdEmail;
        this.userContact = userContact;
        this.userGender = userGender;
        this.profileImageURL = profileImageURL;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getStdID() {
        return stdID;
    }

    public String getStdEmail() {
        return stdEmail;
    }

    public String getUserContact() {
        return userContact;
    }

    public String getUserGender() {
        return userGender;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }
}
