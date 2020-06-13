package com.hrtzpi.models.storeorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class ModelStoreOrder {


    @Expose
    @SerializedName("data")
    private Data data;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("status")
    private boolean status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
