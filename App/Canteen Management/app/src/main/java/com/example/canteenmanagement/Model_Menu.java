package com.example.canteenmanagement;

public class Model_Menu {

    private int img;
    private String itemName;

    public Model_Menu(int img, String itemName) {
        this.img = img;
        this.itemName = itemName;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
