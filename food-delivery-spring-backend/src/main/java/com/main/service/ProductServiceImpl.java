package com.main.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.main.entity.Product;
import com.main.exceptions.CategoryException;
import com.main.exceptions.ProductException;
import com.main.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepo;
	private CategoryService categoryService;
	
	@Value("${upload.dir}")
	private String uploadDir;
	
	

	public ProductServiceImpl(ProductRepository productRepo, CategoryService categoryService) {
		this.productRepo = productRepo;
		this.categoryService = categoryService;
	}

	@Transactional
	@Override
	public Product addProductOrUpdate(MultipartFile image, int price, int categoryId, String itemName, String calories,
			String description, List<String> ingredients, Integer productId) throws IOException, ProductException, CategoryException {
		
		Product product = null;
		String imageName = null;
		
		if(productId != null) 
			product = findById(categoryId);
		
		
		if(image!= null) {
			Path path = Paths.get(uploadDir);
			
			if(!Files.exists(path))
				Files.createDirectories(path);

			 imageName = UUID.randomUUID().toString() +image.getOriginalFilename();
			
			Files.copy(image.getInputStream(), path.resolve(imageName), StandardCopyOption.REPLACE_EXISTING);
		}
		
		if(product == null)
			product = new Product();
		
		if(imageName != null)
			product.setImage(imageName);
		
		product.setCalories(calories);
		product.setPrice(price);
		product.setIngredients(ingredients);
		product.setName(itemName);
		product.setStatus(true);
		product.setCategory(categoryService.findById(categoryId));
		
		product = productRepo.save(product);
		
		if(product == null)
			throw new ProductException(productId != null ? "Failed to update product" : "Fail to add product");
		
		return product;
	}

	@Transactional
	@Override
	public boolean deleteProduct(int productId) throws ProductException {
			
			if(!productRepo.existsById(productId))
				throw new ProductException("Product with id " + productId + " doesnt exists");
				
				productRepo.deleteById(productId);
				
				return true;
	}

	@Transactional
	@Override
	public boolean disableProduct(int productId) throws ProductException {
		 Product product = findById(productId);
		 product.setStatus(false);
		 
		 product = productRepo.save(product);
		 if(product == null)
			 throw new ProductException("Fail to disable product");
		 
		 return true;
	}

	@Transactional
	@Override
	public boolean enableProduct(int productId) throws ProductException {
		
		Product product = findById(productId);
		 product.setStatus(true);
		 product = productRepo.save(product);
		 
		 if(product == null)
			 throw new ProductException("Fail to disable product");

		 return true;
	}

	@Override
	public List<Product> findAllProducts() {
		return productRepo.findAll();
	}

	@Override
	public Product findById(int productId) throws ProductException {
		 
		Optional<Product> opt = productRepo.findById(productId);
		
		if(!opt.isPresent())
			throw new ProductException("Product with id " + productId + " doesnt exists");
		
		return opt.get();
	}

	@Override
	public List<Product> findByCategoryId(int categoryId) {
		
		return productRepo.findByCategoryId(categoryId);
	}

	@Override
	public byte[] findImage(String imageName) throws IOException {
		
			var imageFile = new ClassPathResource("static/" + imageName);
			
			
			return StreamUtils.copyToByteArray(imageFile.getInputStream());
	}

	

}
