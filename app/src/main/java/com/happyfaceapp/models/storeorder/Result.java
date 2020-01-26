package com.happyfaceapp.models.storeorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @Expose
    @SerializedName("item")
    private List<Item> item;
    @Expose
    @SerializedName("last_updt")
    private String last_updt;
    @Expose
    @SerializedName("promocode")
    private String promocode;
    @Expose
    @SerializedName("total_amount")
    private String total_amount;
    @Expose
    @SerializedName("client_id")
    private int client_id;
    @Expose
    @SerializedName("order_date")
    private String order_date;
    @Expose
    @SerializedName("id")
    private int id;

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public String getLast_updt() {
        return last_updt;
    }

    public void setLast_updt(String last_updt) {
        this.last_updt = last_updt;
    }

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
