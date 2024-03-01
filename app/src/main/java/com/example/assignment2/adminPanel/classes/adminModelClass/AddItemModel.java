package com.example.assignment2.adminPanel.classes.adminModelClass;

public class AddItemModel {
    private String itemName;
    private String itemPrice;
    private String imageUrl;
    private String itemKey;

    public AddItemModel(String itemName, String itemPrice, String imageUrl, String itemKey) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.imageUrl = imageUrl;
        this.itemKey = itemKey;
    }

    public AddItemModel() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }
}
