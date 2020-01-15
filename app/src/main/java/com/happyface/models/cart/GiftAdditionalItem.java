package com.happyface.models.cart;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class GiftAdditionalItem implements Serializable {

	@SerializedName("cart_id")
	private int cartId;

	@SerializedName("quantity")
	private String quantity;

	@SerializedName("additional")
	private Additional additional;

	@SerializedName("id")
	private int id;

	@SerializedName("additional_id")
	private int additionalId;

	public void setCartId(int cartId){
		this.cartId = cartId;
	}

	public int getCartId(){
		return cartId;
	}

	public void setQuantity(String quantity){
		this.quantity = quantity;
	}

	public String getQuantity(){
		return quantity;
	}

	public void setAdditional(Additional additional){
		this.additional = additional;
	}

	public Additional getAdditional(){
		return additional;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setAdditionalId(int additionalId){
		this.additionalId = additionalId;
	}

	public int getAdditionalId(){
		return additionalId;
	}
}