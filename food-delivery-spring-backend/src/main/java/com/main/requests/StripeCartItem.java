package com.main.requests;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StripeCartItem {

	private String currency;
	private String name;
	private String image;
	private String id;
	private Integer totalPrice;
	private int quantity;

}
