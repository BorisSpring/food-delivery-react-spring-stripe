package com.main.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {

	@Builder
	public Category(Integer id, LocalDateTime created, LocalDateTime lastModified, String name, List<Product> products) {
		super(id, created, lastModified);
		this.name = name;
		this.products = products;
	}

	@Column(nullable = false)
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy="category", cascade = CascadeType.ALL)
	private List<Product> products = new ArrayList<>();


}
