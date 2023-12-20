package com.main.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Getter
@Setter
public class ProductRequest {

    private MultipartFile image;

    @NotNull(message = "Price is required!")
    @Positive(message = "Price must be positive!")
    private double price;

    @NotNull(message = "Category id is required!")
    @Positive(message = "Category is must be positive number")
    private int categoryId;

    @NotBlank(message = "Item name is required!")
    private String itemName;

    @NotBlank(message = "Calories description required!")
    private String calories;

    @NotBlank(message = "Description is required!")
    private String description;

    @NotNull(message = "Ingredients is required!")
    @NotEmpty(message = "Ingredients must not be empty!")
    private List<String> ingredients;

    private Integer productId;
}
