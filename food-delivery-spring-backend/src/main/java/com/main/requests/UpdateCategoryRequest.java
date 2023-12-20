package com.main.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest {

    @NotNull(message = "Category id is required to update category!")
    @Positive(message = "Cateogry id must be positive number!")
    private Integer categoryId;

    @NotBlank(message = "Category name is required!")
    private String categoryName;
}
