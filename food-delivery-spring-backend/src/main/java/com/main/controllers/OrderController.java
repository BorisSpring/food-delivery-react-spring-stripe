package com.main.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.entity.Order;
import com.main.exceptions.OrderException;
import com.main.exceptions.ProductException;
import com.main.requests.OrderRequest;
import com.main.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	

	@GetMapping
	public ResponseEntity<List<Order>> findOrdersHandler(){
		return ResponseEntity.status(HttpStatus.OK).body(orderService.findAllOrders());
	}
	
	
	@PostMapping
	public ResponseEntity<Order> createOrderHandler(@RequestBody OrderRequest req, @RequestHeader("Authorization") String jwt) throws ProductException, OrderException{
		
		return ResponseEntity.status(HttpStatus.OK).body(orderService.createOrder(req.getOrderItems(), req.getUserId() ,jwt, req.getDeliveryAdress()));
	}
	
	@PostMapping("/cancel/{orderId}")
	public ResponseEntity<Boolean> cancelOrderHandler(@PathVariable int orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
		
		return ResponseEntity.status(HttpStatus.OK).body(orderService.cancelOrder(jwt, orderId));
	}
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<Boolean> deleteOrderHandler(@PathVariable int orderId) throws OrderException{
		
		return ResponseEntity.status(HttpStatus.OK).body(orderService.deleteOrder(orderId));
	}
	
	@PostMapping("/orderStatus/{orderId}")
	public ResponseEntity<Boolean> updateOrderStatus(@PathVariable int orderId, @RequestBody String orderStatus) throws OrderException{
		
		orderStatus = orderStatus.replaceAll("\"", "");
		
		return ResponseEntity.status(HttpStatus.OK).body(orderService.setOrderStatus(orderStatus, orderId));
	}
}
