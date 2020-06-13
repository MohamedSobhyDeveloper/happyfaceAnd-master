package com.hrtzpi.models.category_models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@SerializedName("image_icon")
	private String imageIcon;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("childs")
	private List<ChildsItem> childs;

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

	public void setChilds(List<ChildsItem> childs){
		this.childs = childs;
	}

	public List<ChildsItem> getChilds(){
		return childs;
	}
}