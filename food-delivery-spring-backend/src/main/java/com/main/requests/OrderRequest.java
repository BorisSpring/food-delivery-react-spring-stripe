package com.main.requests;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {

	private List<OrderItemRequest> orderItems = new ArrayList<>();
	private int userId;
	public boolean isPaid;
	private String deliveryAdress;
	
	
	
	
	public String getDeliveryAdress() {
		return deliveryAdress;
	}
	public void setDeliveryAdress(String deliveryAdress) {
		this.deliveryAdress = deliveryAdress;
	}
	public boolean isPaid() {
		return isPaid;
	}
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public List<OrderItemRequest> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemRequest> orderItems) {
		this.orderItems = orderItems;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
}
