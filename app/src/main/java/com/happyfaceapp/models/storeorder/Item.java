package com.happyfaceapp.models.storeorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @Expose
    @SerializedName("additional_price")
    private int additional_price;
    @Expose
    @SerializedName("dateins")
    private String dateins;
    @Expose
    @SerializedName("quantity")
    private String quantity;
    @Expose
    @SerializedName("price")
    private String price;
    @Expose
    @SerializedName("product_id")
    private int product_id;
    @Expose
    @SerializedName("order_id")
    private int order_id;
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("product_name")
    private String product_name;
    @Expose
    @SerializedName("product_no")
    private String product_no;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_no() {
        return product_no;
    }

    public void setProduct_no(String product_no) {
        this.product_no = product_no;
    }

    public int getAdditional_price() {
        return additional_price;
    }

    public void setAdditional_price(int additional_price) {
        this.additional_price = additional_price;
    }

    public String getDateins() {
        return dateins;
    }

    public void setDateins(String dateins) {
        this.dateins = dateins;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
