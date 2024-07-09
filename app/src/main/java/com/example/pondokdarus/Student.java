package com.example.pondokdarus;

public class Student {
    private String fullname;
    private String icNum;
    private String form;
    private String userId; // Assuming you want to store the user ID as well

    // Default constructor required for calls to DataSnapshot.getValue(Student.class)
    public Student() {}

    public Student(String fullname, String icNum, String form, String userId) {
        this.fullname = fullname;
        this.icNum = icNum;
        this.form = form;
        this.userId = userId;
    }

    // Getters and setters
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIcNum() {
        return icNum;
    }

    public void setIcNum(String icNum) {
        this.icNum = icNum;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
