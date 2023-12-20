package com.main.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {

    private String name;

    private String image;

    private int quantity;

    private double totalPrice;
}
