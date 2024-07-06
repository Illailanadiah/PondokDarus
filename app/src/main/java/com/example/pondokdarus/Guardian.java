package com.example.pondokdarus;

public class Guardian {

    private String fullname;
    private String icNum;
    private String phoneNum;
    private boolean agreementChecked;

    public Guardian() {
        // Default constructor required for calls to DataSnapshot.getValue(Guardian.class)
    }

    public Guardian(String fullname, String icNum, String phoneNum, boolean agreementChecked) {
        this.fullname = fullname;
        this.icNum = icNum;
        this.phoneNum = phoneNum;
        this.agreementChecked = agreementChecked;
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
