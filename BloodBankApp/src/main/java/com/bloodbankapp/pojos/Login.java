package com.bloodbankapp.pojos;

public class Login extends Response{
	
	private int id;
	
	private long phNo;
	private String password;
	private String sessionToken;
	
	
	
	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	public int getUserId() {
		return id;
	}
	public void setUserId(int id) {
		this.id = id;
	}
	
	public long getPhNo() {
		return phNo;
	}
	public void setPhNo(long phNo) {
		this.phNo = phNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
