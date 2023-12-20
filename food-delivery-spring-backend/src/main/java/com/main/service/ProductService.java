package com.main.service;

import java.io.IOException;
import java.util.List;

import com.main.requests.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import com.main.entity.Product;
import com.main.exceptions.CategoryException;
import com.main.exceptions.ProductException;

public interface ProductService {

	
	 void deleteProduct(int productId) throws ProductException;
	
	 void disableProduct(int productId) throws ProductException;
	
	 void enableProduct(int productId)  throws ProductException;
	
	 Product findById(int productId) throws ProductException;
	 
	 Page<Product> findAllProducts(PageRequest pageRequest);

	 Product addProductOrUpdate(ProductRequest productRequest)  throws IOException, ProductException, CategoryException;

	 List<Product> findByCategoryId(int categoryId);

	 byte [] findImage(String imageName) throws IOException;
}
