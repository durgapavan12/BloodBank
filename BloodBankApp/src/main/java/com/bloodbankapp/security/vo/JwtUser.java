package com.bloodbankapp.security.vo;

public class JwtUser {

	public static final int ADMIN = 1;
	public static final int USER = 2;
	
	private long userPhno;
	

	private long userId;
	private String roles;
	private long expiry;
	private int userType;

	

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getUserPhno() {
		return userPhno;
	}

	public void setUserPhno(long userPhno) {
		this.userPhno = userPhno;
	}
	
	

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public long getExpiry() {
		return expiry;
	}

	public void setExpiry(long expiry) {
		this.expiry = expiry;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

}
