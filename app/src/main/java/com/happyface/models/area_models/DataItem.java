package com.happyface.models.area_models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@SerializedName("VIP_value")
	private String vIPValue;

	@SerializedName("Delivery Day (1)")
	private String deliveryDay1;

	@SerializedName("Delivery Day")
	private String deliveryDay;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("ORGINAL_value")
	private String oRGINALValue;

	public void setVIPValue(String vIPValue){
		this.vIPValue = vIPValue;
	}

	public String getVIPValue(){
		return vIPValue;
	}

	public void setDeliveryDay1(String deliveryDay1){
		this.deliveryDay1 = deliveryDay1;
	}

	public String getDeliveryDay1(){
		return deliveryDay1;
	}

	public void setDeliveryDay(String deliveryDay){
		this.deliveryDay = deliveryDay;
	}

	public String getDeliveryDay(){
		return deliveryDay;
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

	public void setORGINALValue(String oRGINALValue){
		this.oRGINALValue = oRGINALValue;
	}

	public String getORGINALValue(){
		return oRGINALValue;
	}
}