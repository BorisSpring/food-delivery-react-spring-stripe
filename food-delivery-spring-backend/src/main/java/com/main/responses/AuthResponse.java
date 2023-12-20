package com.main.responses;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthResponse {

	private boolean isAuth;
	private String jwt;
	
}
