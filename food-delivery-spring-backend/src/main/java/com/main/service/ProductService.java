package com.main.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.main.entity.Product;
import com.main.exceptions.CategoryException;
import com.main.exceptions.ProductException;

public interface ProductService {

	
	 boolean deleteProduct(int productId) throws ProductException;
	
	 boolean disableProduct(int productId) throws ProductException;
	
	 boolean enableProduct(int productId)  throws ProductException;
	
	 Product findById(int productId) throws ProductException;
	 
	 List<Product> findAllProducts();

	Product addProductOrUpdate(MultipartFile image, int price, int categoryId, String itemName, String calories,
			String description, List<String> ingredients, Integer productId)  throws IOException, ProductException, CategoryException;

	List<Product> findByCategoryId(int categoryId);

	byte [] findImage(String imageName) throws IOException;
}
