package com.example.canteenmanagement;

public class ModelAll {
    private String date;
    private int totalAmount;

    public ModelAll() {
        // Default constructor required for Firestore
    }

    public ModelAll(String date, int totalAmount) {
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
