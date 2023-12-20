package com.main.dto;

import com.main.entity.OrderItem;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Integer id;

    private LocalDateTime created;

    private LocalDateTime deliveredTime;

    private String orderStatus;

    private LocalDateTime estimatedDeliveryTime;

    private String deliveryAdress;

    private String phoneNumber;

    private boolean isPaid;

    private String firstName;

    private String lastName;

    private String customerEmail;

    private int totalQuantity;

    private double totalPrice;

    private List<OrderItemDto> orderItems;

}
