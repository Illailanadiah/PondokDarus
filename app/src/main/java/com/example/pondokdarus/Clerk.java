package com.example.pondokdarus;

public class Clerk {
    private String staffId;

    public Clerk() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Clerk(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
