package com.example.final_emts;

public class RequestData {
    private String requestId;
    private String userEmail;
    private String requestDescription;
    private String requestTitle;

    public RequestData(String requestId, String requestTitle, String userEmail, String requestDescription) {
        this.requestId = requestId;
        this.userEmail = userEmail;
        this.requestTitle = requestTitle;
        this.requestDescription = requestDescription;
    }

    // Getter and setter methods for fields

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }





    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public void setRequestTitle(String requestTitle) {
        this.requestTitle = requestTitle;
    }
}
