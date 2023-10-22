package com.main.requests;

import java.util.ArrayList;
import java.util.List;

import com.main.dto.UserDTO;


public class StripePaymentRequest {

	
	private List<StripeCartItem> cart = new ArrayList<>();
	private UserDTO user;
	private int totalPrice;
	
	
	public List<StripeCartItem> getCart() {
		return cart;
	}
	public void setCart(List<StripeCartItem> cart) {
		this.cart = cart;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@Override
	public String toString() {
		return "StripePaymentRequest [cart=" + cart + ", user=" + user + ", totalPrice=" + totalPrice + "]";
	}
	
	
	
	
}
