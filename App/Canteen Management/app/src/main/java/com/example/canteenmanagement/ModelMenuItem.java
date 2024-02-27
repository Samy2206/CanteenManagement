package com.example.canteenmanagement;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelMenuItem implements Parcelable {
    private String imgUrl; // URL for the image
    private String foodName; // Name of the food item
    private String foodPrice; // Price of the food item
    private int quantity; // Quantity of the food item

    public ModelMenuItem() {
    }

    public ModelMenuItem(String imgUrl, String foodName, String foodPrice) {
        this.imgUrl = imgUrl;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
    }

    public ModelMenuItem(String foodName, String foodPrice) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
    }


    public ModelMenuItem(String foodName, String foodPrice, int quantity) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.quantity = quantity;
    }

    public ModelMenuItem(String imgUrl, String foodName, String foodPrice, int quantity) {
        this.imgUrl = imgUrl;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.quantity = quantity;
    }

    // Getters and setters
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Parcelable implementation
    protected ModelMenuItem(Parcel in) {
        imgUrl = in.readString();
        foodName = in.readString();
        foodPrice = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<ModelMenuItem> CREATOR = new Creator<ModelMenuItem>() {
        @Override
        public ModelMenuItem createFromParcel(Parcel in) {
            return new ModelMenuItem(in);
        }

        @Override
        public ModelMenuItem[] newArray(int size) {
            return new ModelMenuItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgUrl);
        dest.writeString(foodName);
        dest.writeString(foodPrice);
        dest.writeInt(quantity);
    }
}
