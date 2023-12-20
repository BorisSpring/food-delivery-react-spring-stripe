package com.main.controllers;

import java.io.IOException;
import java.util.List;

import com.main.requests.ProductRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.main.entity.Product;
import com.main.exceptions.CategoryException;
import com.main.exceptions.ProductException;
import com.main.service.ProductService;

@RestController
@RequestMapping(path = "/api/products", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping("/image/{imageName}")
	public ResponseEntity<?> findImageHandler(@PathVariable String imageName) throws IOException{
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(productService.findImage(imageName));
	}
	
	@PostMapping
	public ResponseEntity<Product> addProductHandler(@Valid @ModelAttribute ProductRequest productRequest) throws IOException, ProductException, CategoryException{
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProductOrUpdate(productRequest));
	}
	
	@GetMapping
	public ResponseEntity<Page<Product>> findAllProducts(@Positive(message = "Page number must be positive") @RequestParam(name ="pageNumber", required = false, defaultValue = "1") Integer pageNumber,
														 @Positive(message = "Page size must be positive") @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize){
		return ResponseEntity.ok(productService.findAllProducts(PageRequest.of((pageNumber - 1), pageSize)));
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProductHandler(@Positive(message = "Product id must be positive number!") @RequestParam int productId) throws ProductException{
		productService.deleteProduct(productId);
	}
	
	@PutMapping("/disable")
	@ResponseStatus(HttpStatus.OK)
	public void disableProductHandler(@Positive(message = "Product id must be positive number!") @RequestParam int productId) throws ProductException{
		productService.disableProduct(productId);
	}
	
	@PutMapping("/enable")
	@ResponseStatus(HttpStatus.OK)
	public void enableProductHandler(@Positive(message = "Product id must be positive number!") @RequestParam int productId) throws ProductException{
		productService.enableProduct(productId);
	}
	
	@GetMapping("/category")
	public ResponseEntity<List<Product>> findProductsByCategory(@Positive(message = "Category id must be positive number!") @RequestParam int categoryId){
		return ResponseEntity.ok(productService.findByCategoryId(categoryId));
	}
}
