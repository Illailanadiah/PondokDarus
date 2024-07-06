package com.example.pondokdarus;

public class Student {
    private String fullname;
    private String icNum;
    private String form;

    public Student() {
        // Default constructor required for calls to DataSnapshot.getValue(Student.class)
        this.fullname = "";
        this.icNum = "";
        this.form = "";
    }

    public Student(String fullname, String icNum, String form) {
        this.fullname = fullname;
        this.icNum = icNum;
        this.form = form;
    }

    public String getFullname() {
        return fullname;
    }

    public String getIcNum() {
        return icNum;
    }

    public String getForm() {
        return form;
    }
}