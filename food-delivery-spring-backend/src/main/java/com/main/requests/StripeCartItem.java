package com.main.requests;


public class StripeCartItem {

	private String currency;
	private String name;
	private String image;
	private String id;
	private Integer totalPrice;
	private int quantity;
	
	
	
	
	
	public String getCurrency() {
		return currency;
	}





	public void setCurrency(String currency) {
		this.currency = currency;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public String getImage() {
		return image;
	}





	public void setImage(String image) {
		this.image = image;
	}





	public String getId() {
		return id;
	}





	public void setId(String id) {
		this.id = id;
	}





	public Integer getTotalPrice() {
		return totalPrice;
	}





	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}





	public int getQuantity() {
		return quantity;
	}





	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}





	@Override
	public String toString() {
		return "StripeCartItem [currency=" + currency + ", name=" + name + ", image=" + image
				+ ", productId=" + id + "]";
	}
	
	
}
