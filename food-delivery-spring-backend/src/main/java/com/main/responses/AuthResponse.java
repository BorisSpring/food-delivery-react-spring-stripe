package com.main.responses;

public class AuthResponse {

	private boolean isAuth;
	private String jwt;
	
	public AuthResponse(boolean isAuth, String jwt) {
		this.isAuth = isAuth;
		this.jwt = jwt;
	}

	public boolean isAuth() {
		return isAuth;
	}

	public void setAuth(boolean isAuth) {
		this.isAuth = isAuth;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	
	
	
}
