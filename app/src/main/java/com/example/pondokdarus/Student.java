package com.example.pondokdarus;

public class Student {
    private String fullname;
    private String icNum;
    private String form;

    public Student() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Student(String fullname, String icNum, String form) {
        this.fullname = fullname;
        this.icNum = icNum;
        this.form = form;
    }

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
}
