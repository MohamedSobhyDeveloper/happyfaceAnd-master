package com.hrtzpi.models.message_models;

public class MessageResponse{
	private Data data;
	private String message;
	private String error;
	private boolean status;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}
