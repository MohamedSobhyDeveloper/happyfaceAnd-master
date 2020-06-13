package com.hrtzpi.models.search_products;

import java.io.Serializable;

public class Color implements Serializable {
	private String code;
	private String hastag;
	private String name;
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

	@Override
 	public String toString(){
		return 
			"Color{" + 
			"code = '" + code + '\'' + 
			",hastag = '" + hastag + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
