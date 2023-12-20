package com.main.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

	@NotNull(message = "Email is required!")
	@Email
	private String email;

	@NotBlank(message = "Password is required!")
	@Size(min = 5, max = 10, message = "Password size must be between 5 and 10 chars!")
	private String password;
	
}
