package com.happyfaceapp.models.cart;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class AddData implements Serializable {
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
