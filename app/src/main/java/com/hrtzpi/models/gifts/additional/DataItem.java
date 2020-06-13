package com.hrtzpi.models.gifts.additional;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class DataItem {


    private boolean isSelected;
    @SerializedName("image")
    private String image;

    @SerializedName("price")
    private String price;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @Expose
    private String quantity;
    private int amount = 1;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getAmount() {
        return Integer.parseInt(quantity == null ? "1" : quantity);
    }

    public void setAmount(int amount) {
        this.quantity = "" + amount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}