package com.main.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends  BaseEntity {

	@Builder
	public Order(Integer id, LocalDateTime created, LocalDateTime lastModified, LocalDateTime deliveredTime, String orderStatus, LocalDateTime estimatedDeliveryTime, String deliveryAdress, String phoneNumber, boolean isPaid, User user, List<OrderItem> orderItems, int totalQuantity, int totalPrice) {
		super(id, created, lastModified);
		this.deliveredTime = deliveredTime;
		this.orderStatus = orderStatus;
		this.estimatedDeliveryTime = estimatedDeliveryTime;
		this.deliveryAdress = deliveryAdress;
		this.phoneNumber = phoneNumber;
		this.isPaid = isPaid;
		this.user = user;
		this.orderItems = orderItems;
		this.totalQuantity = totalQuantity;
		this.totalPrice = totalPrice;
	}

	private LocalDateTime deliveredTime;

	@Column(nullable = false)
	private String orderStatus;

	@Column(nullable = false)
	private LocalDateTime estimatedDeliveryTime;

	@Column(nullable = false)
	private String deliveryAdress;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private boolean isPaid;
	
	@JsonIgnore
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="userId")
	private User user;
	
	@OneToMany(mappedBy="order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

	@Column(nullable = false)
	private int totalQuantity;

	@Column(nullable = false)
	private double totalPrice;

}
