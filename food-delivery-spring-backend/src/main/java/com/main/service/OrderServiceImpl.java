package com.main.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.main.dto.OrderDto;
import com.main.entity.User;
import com.main.exceptions.ResourceNotFoundException;
import com.main.mappers.OrderMapper;
import com.main.requests.CreateOrderRequest;
import com.main.requests.UpdateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.main.dto.UserDto;
import com.main.entity.Order;
import com.main.entity.OrderItem;
import com.main.entity.Product;
import com.main.exceptions.OrderException;
import com.main.exceptions.ProductException;
import com.main.repository.OrderRepository;
import com.main.requests.OrderItemRequest;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepository orderRepo;
	private final UserService userService;
	private final ProductService productService;
	private final OrderMapper orderMapper;


	@Override
	public Page<OrderDto> findAllOrders(Integer pageNumber, Integer pageSize) {
		return orderRepo.findAll(PageRequest.of((pageNumber - 1), pageSize)).map(orderMapper::orderToOrderDto);
	}

	@Transactional
	@Override
	public void setOrderStatus(String status, int orderId) throws OrderException {
		
		Order order = findById(orderId);
		if(status.equals("DELIVERED")) {
			order.setDeliveredTime(LocalDateTime.now());
			order.setPaid(true);
		}
		order.setOrderStatus(status);
		orderRepo.save(order);
	}

	@Transactional
	@Override
	public void deleteOrder(int orderId) throws OrderException {
		if(!orderRepo.existsById(orderId))
			throw new OrderException("Order with id " + orderId + "doesnt exists");

		orderRepo.deleteById(orderId);
	}

	@Override
	public Order findById(int orderId) throws OrderException {
		return orderRepo.findById(orderId)
					.orElseThrow(() -> new ResourceNotFoundException("Order with id " + orderId + " not found!"));
	}

	@Transactional
	@Override
	public Order createOrder(String jwt, CreateOrderRequest createOrderRequest) throws  ProductException {

		User user = userService.findById(userService.getUserIdFromToken(jwt));
		Order order = new Order();
			
			List<OrderItem> orderItems = new ArrayList<>();
			
			for(OrderItemRequest item : createOrderRequest.getOrderItems()) {
				Product product = productService.findById(item.getProductId());
				orderItems.add(OrderItem.builder()
							.product(product)
							.quantity(item.getTotalQuantity())
							.totalPrice((item.getTotalQuantity() * product.getPrice()) + 2)
							.order(order)
							.build());
			}

			order.setDeliveryAdress(createOrderRequest.getDeliveryAdress());
			order.setPaid(createOrderRequest.isPaid);
			order.setEstimatedDeliveryTime(LocalDateTime.now().plusHours(1));
			order.setUser(user);
			order.setOrderItems(orderItems);
			order.setTotalQuantity( orderItems.stream().mapToInt(OrderItem::getQuantity).sum());
			order.setTotalPrice( orderItems.stream().mapToDouble(OrderItem::getTotalPrice).sum());
			order.setOrderStatus("ACCEPTED");
			order.setCreated(LocalDateTime.now());
			
			return orderRepo.save(order);
	}

	@Transactional
	@Override
	public void cancelOrder(String jwt, int orderId) throws OrderException {
		UserDto user = userService.getUserFromToken(jwt);

		Order order = findById(orderId);
		if(order.getUser().getId().equals(user.getId()))
			throw new OrderException("User not related to this order");

		order.setOrderStatus("CANCELED");
		orderRepo.save(order);
	}

	@Override
	public OrderDto updateOrder(UpdateOrderRequest updateOrderRequest) throws OrderException {
		Order order = findById(updateOrderRequest.getId());
		order.setDeliveryAdress(updateOrderRequest.getDeliveryAdress());
		order.setPhoneNumber(updateOrderRequest.getPhoneNumber());
		return  orderMapper.orderToOrderDto(orderRepo.save(order));
	}

}
