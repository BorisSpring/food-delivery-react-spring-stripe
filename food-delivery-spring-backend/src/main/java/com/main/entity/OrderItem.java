package com.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItem extends BaseEntity {


	@Builder
	public OrderItem(Integer id, LocalDateTime created, LocalDateTime lastModified, int quantity, double totalPrice, Product product, Order order) {
		super(id, created, lastModified);
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.product = product;
		this.order = order;
	}

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private double totalPrice;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="productId")
	private Product product;
	
	@JsonIgnore
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
	@JoinColumn(name="orderId")
	private Order order;

}
