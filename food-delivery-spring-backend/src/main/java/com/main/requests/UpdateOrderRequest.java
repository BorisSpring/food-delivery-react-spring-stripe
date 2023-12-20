package com.main.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateOrderRequest {

    @NotNull
    private int id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String customerEmail;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String deliveryAdress;
    @NotBlank
    private String orderStatus;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private LocalDateTime estimatedDeliveryTime;
}
