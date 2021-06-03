package com.example.notesapp.Firebase;

public class Users {
    private String uid;
    private String username;
    private String email;

    public Users() {
    }

    private String password;

    public Users(String uid,String username, String email, String password) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
