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
public class User extends BaseEntity {

	@Builder
	public User(Integer id, LocalDateTime created, LocalDateTime lastModified, String email, String password, boolean enabled, String firstName, String lastName, List<Order> orders, Authority authority, String imageName) {
		super(id, created, lastModified);
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.firstName = firstName;
		this.lastName = lastName;
		this.orders = orders;
		this.authority = authority;
		this.imageName = imageName;
	}

	@Column(nullable = false, unique = true)
	private String email;
	
	@JsonIgnore
	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private boolean enabled;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@JsonIgnore
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private List<Order> orders = new ArrayList<>();
	
	@ManyToOne(cascade  = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="authority_id")
	private Authority authority;

	@Column(nullable = false)
	private String imageName;
}
