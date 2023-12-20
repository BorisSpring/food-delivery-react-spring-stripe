package com.main.requests;

import java.util.ArrayList;
import java.util.List;

import com.main.dto.UserDto;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StripePaymentRequest {

	private List<StripeCartItem> cart = new ArrayList<>();
	private UserDto user;
	private double totalPrice;

}
