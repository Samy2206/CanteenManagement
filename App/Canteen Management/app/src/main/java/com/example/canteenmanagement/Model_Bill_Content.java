package com.example.canteenmanagement;

public class Model_Bill_Content {
    private String billNumber;
    private String billingMode;
    private int billAmount;
    private String billName;
    private String dateTime;
    private String Items;

    public Model_Bill_Content() {
    }

    public Model_Bill_Content(String billNumber, String billingMode, int billAmount, String billName, String dateTime, String items) {
        this.billNumber = billNumber;
        this.billingMode = billingMode;
        this.billAmount = billAmount;
        this.billName = billName;
        this.dateTime = dateTime;
        this.Items = items;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getBillingMode() {
        return billingMode;
    }

    public void setBillingMode(String billingMode) {
        this.billingMode = billingMode;
    }

    public int getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(int billAmount) {
        this.billAmount = billAmount;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getItems() {
        return Items;
    }

    public void setItems(String items) {
        Items = items;
    }
}
