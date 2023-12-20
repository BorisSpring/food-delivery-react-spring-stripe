package com.main.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequest {

	@NotBlank(message = "Product id required!")
	@Positive(message = "product id must be positive number!")
	private Integer productId;

	@NotBlank(message = "Total quantity required! id required!")
	@Positive(message = "Total quantity must be positive number!")
	private Integer totalQuantity;

	@NotBlank(message = "total price required!")
	@Positive(message = "Price must be positive number!")
	private double totalPrice;

}
