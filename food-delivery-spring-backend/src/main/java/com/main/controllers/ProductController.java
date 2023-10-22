package com.main.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.main.entity.Product;
import com.main.exceptions.CategoryException;
import com.main.exceptions.ProductException;
import com.main.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	
	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/image/{imageName}")
	public ResponseEntity<?> findImageHandler(@PathVariable String imageName) throws IOException{
		
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(productService.findImage(imageName));
	}
	
	@PostMapping
	public ResponseEntity<Product> addProductHandler(@RequestParam(name= "image", required = false)MultipartFile image ,
													@RequestParam(name="productId", required = false) Integer productId,
													@RequestParam("price") int price,
													@RequestParam("categoryId") int categoryId,
													@RequestParam("itemName") String itemName ,
													@RequestParam("calories") String calories,
													@RequestParam("description") String description, 
													@RequestParam("ingredients") List<String> ingredients) throws IOException, ProductException, CategoryException{
		
	

		
		return ResponseEntity.status(HttpStatus.OK).body(productService.addProductOrUpdate(image, price, categoryId, itemName, calories, description, ingredients, productId));
	}
	
	@GetMapping
	public ResponseEntity<List<Product>> findAllProducts(){
		
		return ResponseEntity.status(HttpStatus.OK).body(productService.findAllProducts());
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<Boolean> deleteProductHandler(@PathVariable int productId) throws ProductException{
		
		return ResponseEntity.status(HttpStatus.OK).body(productService.deleteProduct(productId));
	}
	
	@PostMapping("/disable/{productId}")
	public ResponseEntity<Boolean> disableProductHandler(@PathVariable int productId) throws ProductException{
		
		System.out.println("Asdasd");
		return ResponseEntity.status(HttpStatus.OK).body(productService.disableProduct(productId));
	}
	
	@PostMapping("/enable/{productId}")
	public ResponseEntity<Boolean> enableProductHandler(@PathVariable int productId) throws ProductException{
			
		return ResponseEntity.status(HttpStatus.OK).body(productService.enableProduct(productId));
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<Product>> findProductsByCategory(@PathVariable int categoryId){
		
		return ResponseEntity.status(HttpStatus.OK).body(productService.findByCategoryId(categoryId));
	}
}
