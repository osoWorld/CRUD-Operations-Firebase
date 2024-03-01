package com.example.assignment2.customerPanel.classes;

public class CustomerModel {
    private String itemName;
    private String itemPrice;
    private String imageUrl;
    private String itemKey;
    private String isOrderPlaced;

    public CustomerModel(String itemName, String itemPrice, String imageUrl, String itemKey, String isOrderPlaced) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.imageUrl = imageUrl;
        this.itemKey = itemKey;
        this.isOrderPlaced = isOrderPlaced;
    }

    public CustomerModel() {
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

    public String getIsOrderPlaced() {
        return isOrderPlaced;
    }

    public void setIsOrderPlaced(String isOrderPlaced) {
        this.isOrderPlaced = isOrderPlaced;
    }
}
