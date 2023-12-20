package com.main.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

	@Builder
	public Product(Integer id, LocalDateTime created, LocalDateTime lastModified, double price, String name, String image, boolean status, String calories, List<String> ingredients, List<OrderItem> orderItems, Category category) {
		super(id, created, lastModified);
		this.price = price;
		this.name = name;
		this.image = image;
		this.status = status;
		this.calories = calories;
		this.ingredients = ingredients;
		this.orderItems = orderItems;
		this.category = category;
	}

	@Column(nullable = false)
	private double price;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String image;

	@Column(nullable = false)
	private boolean status;

	@Column(nullable = false)
	private String calories;
	
	@ElementCollection
	@CollectionTable(name="product_ingredients", joinColumns = @JoinColumn(name="product_id"))
	private List<String> ingredients = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@ManyToOne( cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="category_id")
	private Category category;
	

	
	
}
