package com.example.assignment2.ModelClasses;

public class RegisterUser {
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userId;

    public RegisterUser() {
        //Default constructor required for Firebase
    }

    public RegisterUser(String userName, String userEmail, String userPassword, String userId) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
