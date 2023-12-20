package com.main.dto;


import com.main.entity.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStatistics {

    private Product product;
    private Long sum;
}
