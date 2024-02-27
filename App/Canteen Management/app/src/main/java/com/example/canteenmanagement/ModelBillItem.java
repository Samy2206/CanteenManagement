package com.example.canteenmanagement;

public class ModelBillItem {
    private String foodName;
    private int quantity;
    private int totalPrice;

    public ModelBillItem() {
        // Empty constructor needed for Firestore
    }

    public ModelBillItem(String foodName, int quantity, int totalPrice) {
        this.foodName = foodName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
