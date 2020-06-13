package com.hrtzpi.models.cart;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class GiftCover implements Serializable {

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("cover_image")
	private String coverImage;

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCoverImage(String coverImage){
		this.coverImage = coverImage;
	}

	public String getCoverImage(){
		return coverImage;
	}
}