package com.hrtzpi.models.cart;

import com.google.gson.annotations.SerializedName;
import com.hrtzpi.models.search_products.Product;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class CartItem implements Serializable {
/*
	@SerializedName("unit")
	private Unit unit;*/

	@SerializedName("product")
	private Product product;

	@SerializedName("quantity")
	private String quantity;

	@SerializedName("color")
	private Color color;

	@SerializedName("price")
	private String price;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("colorcode")
	private String colorcode;

	@SerializedName("id")
	private int id;

	@SerializedName("client_id")
	private String clientId;


	@SerializedName("gift_messegs_to")
	private String giftMessegsTo;

	@SerializedName("gift_cover")
	private GiftCover giftCover;

	@SerializedName("gift_additional")
	private List<GiftAdditionalItem> giftAdditional;

	@SerializedName("additional_price")
	private int additionalPrice;

	@SerializedName("gift_messegs")
	private int giftMessegs;

	@SerializedName("gift_messegs_msg")
	private String giftMessegsMsg;

	@SerializedName("gift_messegs_from")
	private String giftMessegsFrom;

	@SerializedName("addedtime")
	private String addedtime;

	@SerializedName("gifts_covers")
	private int giftsCovers;

	@SerializedName("gift_message")
	private GiftMessage giftMessage;
	/*

	public void setUnit(Unit unit){
		this.unit = unit;
	}

	public Unit getUnit(){
		return unit;
	}
*/
	public void setProduct(Product product){
		this.product = product;
	}

	public Product getProduct(){
		return product;
	}

	public void setQuantity(String quantity){
		this.quantity = quantity;
	}

	public String getQuantity(){
		return quantity;
	}

	public void setColor(Color color){
		this.color = color;
	}

	public Color getColor(){
		return color;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setColorcode(String colorcode){
		this.colorcode = colorcode;
	}

	public String getColorcode(){
		return colorcode;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setClientId(String clientId){
		this.clientId = clientId;
	}

	public String getClientId(){
		return clientId;
	}


	public void setGiftMessegsTo(String giftMessegsTo){
		this.giftMessegsTo = giftMessegsTo;
	}

	public String getGiftMessegsTo(){
		return giftMessegsTo;
	}

	public void setGiftCover(GiftCover giftCover){
		this.giftCover = giftCover;
	}

	public GiftCover getGiftCover(){
		return giftCover;
	}

	public void setGiftAdditional(List<GiftAdditionalItem> giftAdditional){
		this.giftAdditional = giftAdditional;
	}

	public List<GiftAdditionalItem> getGiftAdditional(){
		return giftAdditional;
	}

	public void setAdditionalPrice(int additionalPrice){
		this.additionalPrice = additionalPrice;
	}

	public int getAdditionalPrice(){
		return additionalPrice;
	}

	public void setGiftMessegs(int giftMessegs){
		this.giftMessegs = giftMessegs;
	}

	public int getGiftMessegs(){
		return giftMessegs;
	}

	public void setGiftMessegsMsg(String giftMessegsMsg){
		this.giftMessegsMsg = giftMessegsMsg;
	}

	public String getGiftMessegsMsg(){
		return giftMessegsMsg;
	}

	public void setGiftMessegsFrom(String giftMessegsFrom){
		this.giftMessegsFrom = giftMessegsFrom;
	}

	public String getGiftMessegsFrom(){
		return giftMessegsFrom;
	}

	public void setAddedtime(String addedtime){
		this.addedtime = addedtime;
	}

	public String getAddedtime(){
		return addedtime;
	}

	public void setGiftsCovers(int giftsCovers){
		this.giftsCovers = giftsCovers;
	}

	public int getGiftsCovers(){
		return giftsCovers;
	}

	public void setGiftMessage(GiftMessage giftMessage){
		this.giftMessage = giftMessage;
	}

	public GiftMessage getGiftMessage(){
		return giftMessage;
	}
}