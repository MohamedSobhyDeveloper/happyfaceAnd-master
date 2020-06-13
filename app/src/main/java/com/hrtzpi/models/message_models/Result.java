package com.hrtzpi.models.message_models;

public class Result{
	private String addedmsg;
	private int usrid;
	private int id;
	private String message;

	public void setAddedmsg(String addedmsg){
		this.addedmsg = addedmsg;
	}

	public String getAddedmsg(){
		return addedmsg;
	}

	public void setUsrid(int usrid){
		this.usrid = usrid;
	}

	public int getUsrid(){
		return usrid;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}
