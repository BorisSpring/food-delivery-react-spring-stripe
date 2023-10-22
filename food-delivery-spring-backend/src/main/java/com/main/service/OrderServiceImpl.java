package com.main.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.main.dto.UserDTO;
import com.main.entity.Order;
import com.main.entity.OrderItem;
import com.main.entity.Product;
import com.main.exceptions.OrderException;
import com.main.exceptions.ProductException;
import com.main.repository.OrderRepository;
import com.main.requests.OrderItemRequest;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
	
	private OrderRepository orderRepo;
	private UserService userService;
	private ProductService productService;
	
	
	public OrderServiceImpl(OrderRepository orderRepo, UserService userService, ProductService productService) {
		this.orderRepo = orderRepo;
		this.userService = userService;
		this.productService = productService;
	}

	@Override
	public List<Order> findAllOrders() {
		return orderRepo.findAll();
	}

	@Transactional
	@Override
	public boolean setOrderStatus(String status, int orderId) throws OrderException {
		
		Order order = findById(orderId);
		if(status.equals("DELIVERED")) {
			order.setDeliveredTime(LocalDateTime.now());
			order.setPaid(true);
		}
		order.setOrderStatus(status);
		order = orderRepo.save(order);
		
		if(order == null)
			throw new OrderException("Fail to update order status");
		
		return true;
		
	}

	@Transactional
	@Override
	public boolean deleteOrder(int orderId) throws OrderException {
			
		if(!orderRepo.existsById(orderId))
			throw new OrderException("Order with id " + orderId + "doesnt exists");

		orderRepo.deleteById(orderId);
		
		return true;
	}

	@Override
	public Order findById(int orderId) throws OrderException {
		
		Optional<Order> opt = orderRepo.findById(orderId);
		
		if(!opt.isPresent())
			throw new OrderException("Order with id " + orderId + "doesnt exists");
		
		return opt.get();
	}

	@Transactional
	@Override
	public Order createOrder(List<OrderItemRequest> orderItems, int userId, String jwt, String deliveryAdress) throws OrderException, ProductException {
		
			UserDTO user = userService.getUserFromToken(jwt);
			
			Order order = new Order();
			
			List<OrderItem> items = new ArrayList<>();
			
			for(OrderItemRequest item : orderItems) {
				OrderItem i = new OrderItem();
				Product product = null;
				product = productService.findById(item.getProductId());
				
				i.setProduct(product);
				i.setQuantity(item.getTotalQuantity());
				i.setTotalPrice((item.getTotalQuantity() * product.getPrice()) + 2);
				i.setOrder(order);
				items.add(i);
			}
					
			
			
			var totalQuantity  = items.stream().mapToInt(OrderItem::getQuantity).sum();
			var totalPrice = items.stream().mapToInt(OrderItem::getTotalPrice).sum();
			
			order.setDeliveryAdress(deliveryAdress);
			order.setPaid(false);
			order.setEstimatedDeliveryTime(LocalDateTime.now().plusHours(1));
			order.setUser(userService.findById(user.getId()));
			order.setOrderItems(items);
			order.setTotalQuantity(totalQuantity);
			order.setTotalPrice(totalPrice);
			order.setOrderStatus("ACCEPTED");
			order.setCreated(LocalDateTime.now());
			
			order = orderRepo.save(order);
			
			if(order == null)
				throw new OrderException("Fail to place order. Try Again");
			
			return order;
	}

	@Transactional
	@Override
	public boolean cancelOrder(String jwt, int orderId) throws OrderException {
		
		UserDTO user = userService.getUserFromToken(jwt);
		
		Order order = findById(orderId);
		
		if(order.getUser().getId() != user.getId())
			throw new OrderException("User not related to this order");
		
		order.setOrderStatus("CANCELED");
		order =orderRepo.save(order);
		
		if(order == null)
			throw new OrderException("Fail to cancel order");
		
		
		return true;
	}

	
}
