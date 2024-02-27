package com.example.canteenmanagement;

public class Model_GenBill {

    String foodName;
    String foodPrice;
    int Quantity;

    public Model_GenBill(String foodName, String foodPrice, int quantity) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        Quantity = quantity;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
