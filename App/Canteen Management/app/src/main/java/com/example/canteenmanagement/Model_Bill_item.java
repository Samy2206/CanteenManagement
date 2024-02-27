package com.example.canteenmanagement;

import java.util.ArrayList;

public class Model_Bill_item {
    private String foodName;
    private int quantity;
    private int totalPrice;
    private ArrayList<Model_Bill_item> arrItems;

    public Model_Bill_item(String foodName, int quantity, int totalPrice,ArrayList<Model_Bill_item> arrItems) {
        this.foodName = foodName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.arrItems = arrItems;

    }

    public ArrayList<Model_Bill_item> getArrItems() {
        return arrItems;
    }

    public void setArrItems(ArrayList<Model_Bill_item> arrItems) {
        this.arrItems = arrItems;
    }

    public Model_Bill_item() {
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
