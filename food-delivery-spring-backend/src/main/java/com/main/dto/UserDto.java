package com.main.dto;

import java.util.List;

import com.main.entity.Order;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDto {

	private Integer id;
	private String email;
	private List<Order> orders;
	private String firstName;
	private String lastName;
	private String authority;
	private String imageName;
	private boolean enabled;
}
