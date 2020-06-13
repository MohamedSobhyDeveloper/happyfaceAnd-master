package com.hrtzpi.models.registration_models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class ErrorRegistrationResponse{

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}