package com.main.requests;

import com.main.entity.OrderItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    @NotNull(message = "Order items must not be null!")
    @NotEmpty(message = "Order items must not be empty!")
    private List<OrderItemRequest> orderItems;

    @NotBlank(message = "Delivery adress must not be empty!")
    private String deliveryAdress;

    @NotNull(message = "Information about payment is required!")
    public boolean isPaid;
}
