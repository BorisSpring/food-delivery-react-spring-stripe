package com.main.service;

import java.util.List;

import com.main.entity.Order;
import com.main.exceptions.OrderException;
import com.main.exceptions.ProductException;
import com.main.requests.OrderItemRequest;

public interface OrderService {

	
	 List<Order> findAllOrders();
	
	 boolean setOrderStatus(String status, int orderId) throws OrderException;
	
	 boolean deleteOrder(int orderId) throws OrderException;
	
	 Order findById(int orderId) throws OrderException;

	Order createOrder(List<OrderItemRequest> orderItems, int userId, String jwt , String adress) throws ProductException, OrderException;

	boolean cancelOrder(String jwt, int orderId) throws OrderException;
}
