package com.happyface.models.search_products;

import java.io.Serializable;
import java.util.List;

public class ProDetails implements Serializable {
	private Color color;
	private String count;
	private String discount;
	private String subcode;
	private String remark;
	private String newprice;
	private List<MeasureItem> measure;
	private Object serial;
	private String basicunit;
	private String price;
	private String unitsize;
	private int id;
	private String pId;

	public void setColor(Color color){
		this.color = color;
	}

	public Color getColor(){
		return color;
	}

	public void setCount(String count){
		this.count = count;
	}

	public String getCount(){
		return count;
	}

	public void setDiscount(String discount){
		this.discount = discount;
	}

	public String getDiscount(){
		return discount;
	}

	public void setSubcode(String subcode){
		this.subcode = subcode;
	}

	public String getSubcode(){
		return subcode;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return remark;
	}

	public void setNewprice(String newprice){
		this.newprice = newprice;
	}

	public String getNewprice(){
		return newprice;
	}

	public void setMeasure(List<MeasureItem> measure){
		this.measure = measure;
	}

	public List<MeasureItem> getMeasure(){
		return measure;
	}

	public void setSerial(Object serial){
		this.serial = serial;
	}

	public Object getSerial(){
		return serial;
	}

	public void setBasicunit(String basicunit){
		this.basicunit = basicunit;
	}

	public String getBasicunit(){
		return basicunit;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setUnitsize(String unitsize){
		this.unitsize = unitsize;
	}

	public String getUnitsize(){
		return unitsize;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setPId(String pId){
		this.pId = pId;
	}

	public String getPId(){
		return pId;
	}

	@Override
 	public String toString(){
		return 
			"ProDetails{" + 
			"color = '" + color + '\'' + 
			",count = '" + count + '\'' + 
			",discount = '" + discount + '\'' + 
			",subcode = '" + subcode + '\'' + 
			",remark = '" + remark + '\'' + 
			",newprice = '" + newprice + '\'' + 
			",measure = '" + measure + '\'' + 
			",serial = '" + serial + '\'' + 
			",basicunit = '" + basicunit + '\'' + 
			",price = '" + price + '\'' + 
			",unitsize = '" + unitsize + '\'' + 
			",id = '" + id + '\'' + 
			",p_id = '" + pId + '\'' + 
			"}";
		}
}