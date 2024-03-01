package com.example.assignment2.ModelClasses;

public class RegisterUser {
    private String userName;
    private String userEmail;
    private String userPassword;
    private String role;
    private String userId;

    public RegisterUser() {
        //Default constructor required for Firebase
    }

    public RegisterUser(String userName, String userEmail, String userPassword, String role, String userId) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
