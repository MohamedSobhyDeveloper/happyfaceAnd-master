package com.happyfaceapp.models.category_models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ChildsItem{

	@SerializedName("categ_id")
	private String categId;

	@SerializedName("image_icon")
	private String imageIcon;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	public void setCategId(String categId){
		this.categId = categId;
	}

	public String getCategId(){
		return categId;
	}

	public void setImageIcon(String imageIcon){
		this.imageIcon = imageIcon;
	}

	public String getImageIcon(){
		return imageIcon;
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