package com.hrtzpi.models.login_models;

public class Data{
	private User user;
	private String token;

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}
}
