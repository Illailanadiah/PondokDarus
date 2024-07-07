package com.example.pondokdarus;

public class User {
    private String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
