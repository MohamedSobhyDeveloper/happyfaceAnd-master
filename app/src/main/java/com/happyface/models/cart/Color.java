package com.happyface.models.cart;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Color implements Serializable {

	@SerializedName("code")
	private String code;

	@SerializedName("hastag")
	private String hastag;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setHastag(String hastag){
		this.hastag = hastag;
	}

	public String getHastag(){
		return hastag;
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
}