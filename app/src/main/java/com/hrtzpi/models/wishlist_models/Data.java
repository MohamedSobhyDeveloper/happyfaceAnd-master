package com.hrtzpi.models.wishlist_models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("wishlist")
	private boolean wishlist;

	public void setWishlist(boolean wishlist){
		this.wishlist = wishlist;
	}

	public boolean isWishlist(){
		return wishlist;
	}
}