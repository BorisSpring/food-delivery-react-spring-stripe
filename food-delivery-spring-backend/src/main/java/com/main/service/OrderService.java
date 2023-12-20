package com.main.service;


import com.main.dto.OrderDto;
import com.main.entity.Order;
import com.main.exceptions.OrderException;
import com.main.exceptions.ProductException;
import com.main.requests.CreateOrderRequest;
import com.main.requests.UpdateOrderRequest;
import org.springframework.data.domain.Page;

public interface OrderService {

	
	 Page<OrderDto> findAllOrders(Integer pageNumber, Integer pageSize);

	 void setOrderStatus(String status, int orderId) throws OrderException;
	
	 void deleteOrder(int orderId) throws OrderException;
	
	 Order findById(int orderId) throws OrderException;

	 Order createOrder(String jwt , CreateOrderRequest createOrderRequest) throws ProductException, OrderException;

	 void cancelOrder(String jwt, int orderId) throws OrderException;

     OrderDto updateOrder(UpdateOrderRequest updateOrderRequest) throws OrderException;
}
