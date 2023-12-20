package com.main.requests;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreateAccountRequest {

	@NotNull(message = "Email is required!")
	@Email
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 5, max = 10, message = "Password size must be between 5 and 10 chars!")
	private String password;

	@NotBlank(message = "Password is required")
	@Size(min = 5, max = 10, message = "Password size must be between 5 and 10 chars!")
	private String repeatedPassword;

	@NotBlank(message = "Password is required")
	private String authority;

	@NotBlank(message = "First Name is required")
	private String firstName;

	@NotBlank(message = "Last Name is required")
	private String lastName;
	

	@AssertTrue
	public boolean isPassword(){
		return password != null && password.equals(repeatedPassword);
	}
}
