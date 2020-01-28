package com.happyfaceapp.models.registration_models;

import com.google.gson.annotations.SerializedName;
import com.happyfaceapp.models.login_models.User;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Data {

    @SerializedName("user")
    private User user;

    @SerializedName("token")
    private String token;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}