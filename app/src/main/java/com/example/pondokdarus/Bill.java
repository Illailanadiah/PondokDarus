package com.example.pondokdarus;

public class Bill {
    private String billName;
    private String billDetails;
    private String amount;
    private String endDate;
    private String form;

    public Bill() {
        // Default constructor required for calls to DataSnapshot.getValue(Bill.class)
    }

    public Bill(String billName, String billDetails, String amount, String endDate, String form) {
        this.billName = billName;
        this.billDetails = billDetails;
        this.amount = amount;
        this.endDate = endDate;
        this.form = form;
    }

    public String getBillName() {
        return billName;
    }

    public String getBillDetails() {
        return billDetails;
    }

    public String getAmount() {
        return amount;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getForm() {
        return form;
    }
}
