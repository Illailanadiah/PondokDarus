package com.example.pondokdarus;

public class User {
    private String email;
    private String fullname;
    private String icNum;
    private String phoneNum;
    private boolean agreementChecked;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String email, String fullname, String icNum, String phoneNum, boolean agreementChecked) {
        this.email = email;
        this.fullname = fullname;
        this.icNum = icNum;
        this.phoneNum = phoneNum;
        this.agreementChecked = agreementChecked;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public boolean isAgreementChecked() {
        return agreementChecked;
    }

    public void setAgreementChecked(boolean agreementChecked) {
        this.agreementChecked = agreementChecked;
    }
}
