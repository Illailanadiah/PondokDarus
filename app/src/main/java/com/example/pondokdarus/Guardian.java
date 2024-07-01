package com.example.pondokdarus;

public class Guardian {
    private String fullname;
    private String icNum;
    private String phoneNum;
    private boolean agreement;

    public Guardian() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Guardian(String fullname, String icNum, String phoneNum, boolean agreement) {
        this.fullname = fullname;
        this.icNum = icNum;
        this.phoneNum = phoneNum;
        this.agreement = agreement;
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

    public boolean isAgreement() {
        return agreement;
    }

    public void setAgreement(boolean agreement) {
        this.agreement = agreement;
    }
}
