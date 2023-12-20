package com.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrderStatisticsDto {

    private String orderStatus;
    private Long numberOfOrder;
}
