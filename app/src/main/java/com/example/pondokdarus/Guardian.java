package com.example.pondokdarus;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Guardian {
    private String fullname;
    private String icNum;
    private String phoneNum;
    private String userId;

    public Guardian() {
        // Default constructor required for calls to DataSnapshot.getValue(Guardian.class)
    }

    public Guardian(String fullname, String icNum, String phoneNum, String userId) {
        this.fullname = fullname;
        this.icNum = icNum;
        this.phoneNum = phoneNum;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
