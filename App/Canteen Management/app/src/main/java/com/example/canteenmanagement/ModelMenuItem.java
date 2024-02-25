package com.example.canteenmanagement;

public class ModelMenuItem {
    private String imgUrl; // Changed data type to String for URL
    private String foodName;
    private String foodPrice;

    public ModelMenuItem() {
    }

    public ModelMenuItem(String imgUrl, String foodName, String foodPrice) {
        this.imgUrl = imgUrl;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
}
