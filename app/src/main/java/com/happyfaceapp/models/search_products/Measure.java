package com.happyfaceapp.models.search_products;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Measure implements Serializable {

    @SerializedName("up_level")
    private String upLevel;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    public void setUpLevel(String upLevel) {
        this.upLevel = upLevel;
    }

    public String getUpLevel() {
        return upLevel;
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