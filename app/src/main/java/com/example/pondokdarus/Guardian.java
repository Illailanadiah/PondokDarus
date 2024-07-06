package com.example.pondokdarus;

public class Guardian {
    private String fullname;
    private String icNum;
    private String phoneNum;
    private boolean isAgreementChecked;

    public Guardian() {
        // Default constructor required for calls to DataSnapshot.getValue(Guardian.class)
    }

    public Guardian(String fullname, String icNum, String phoneNum, boolean isAgreementChecked) {
        this.fullname = fullname;
        this.icNum = icNum;
        this.phoneNum = phoneNum;
        this.isAgreementChecked = isAgreementChecked;
    }

    public String getFullname() {
        return fullname;
    }

    public String getIcNum() {
        return icNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public boolean isAgreementChecked() {
        return isAgreementChecked;
    }
}