package com.main.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Authority extends  BaseEntity{

	@Builder
	public Authority(Integer id, LocalDateTime created, LocalDateTime lastModified, String name, List<User> users) {
		super(id, created, lastModified);
		this.name = name;
		this.users = users;
	}

	@Column(nullable = false, columnDefinition = "varchar(50)")
	public String name;
	
	@OneToMany(mappedBy="authority")
	public List<User> users = new ArrayList<>();

}
