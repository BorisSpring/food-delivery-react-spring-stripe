package com.main.controllers;

import java.util.List;

import com.main.dto.OrderDto;
import com.main.requests.CreateOrderRequest;
import com.main.requests.UpdateOrderRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.main.entity.Order;
import com.main.exceptions.OrderException;
import com.main.exceptions.ProductException;
import com.main.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@Validated
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@GetMapping
	public ResponseEntity<Page<OrderDto>> findOrdersHandler(@Positive(message = "Page number must be positive") @RequestParam(name ="pageNumber", required = false, defaultValue = "1") Integer pageNumber,
															@Positive(message = "Page size must be positive") @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize){
		return ResponseEntity.ok(orderService.findAllOrders(pageNumber, pageSize));
	}

	@PostMapping
	public ResponseEntity<Order> createOrderHandler(@Valid @RequestBody CreateOrderRequest createOrderRequest,
													@RequestHeader("Authorization") String jwt) throws ProductException, OrderException{
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(jwt,createOrderRequest));
	}

	@PutMapping("/update")
	public ResponseEntity<OrderDto> updateOrder(@Valid @RequestBody UpdateOrderRequest updateOrderRequest) throws OrderException {
		return ResponseEntity.ok(orderService.updateOrder(updateOrderRequest));
	}

	@PutMapping("/cancel")
	@ResponseStatus(HttpStatus.OK)
	public void cancelOrderHandler(@Positive(message = "Order id must be positive!") @RequestParam int orderId,
								   @RequestHeader("Authorization") String jwt) throws OrderException{
		orderService.cancelOrder(jwt, orderId);
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOrderHandler(@Positive(message = "Order id must be positive!") @RequestParam int orderId) throws OrderException{
		orderService.deleteOrder(orderId);
	}
	
	@PutMapping("/orderStatus")
	@ResponseStatus(HttpStatus.OK)
	public void updateOrderStatus(@Positive(message = "Order id must be positive!") @RequestParam int orderId,
								  @RequestBody String orderStatus) throws OrderException{
		orderStatus = orderStatus.replaceAll("\"", "");
		System.out.println(orderStatus);
		orderService.setOrderStatus(orderStatus, orderId);
	}
}
