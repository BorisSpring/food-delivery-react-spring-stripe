package com.main.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.main.exceptions.ResourceNotFoundException;
import com.main.requests.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.main.entity.Product;
import com.main.exceptions.CategoryException;
import com.main.exceptions.ProductException;
import com.main.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepo;
	private final CategoryService categoryService;
	
	@Value("${upload.dir}")
	private String uploadDir;
	
	@Transactional
	@Override
	public Product addProductOrUpdate(ProductRequest productRequest) throws IOException, ProductException, CategoryException {
		
		Product product = null;
		String imageName = null;
		
		if(productRequest.getProductId() != null)
			product = findById(productRequest.getProductId());
		
		
		if(productRequest.getImage() != null) {
			Path path = Paths.get(uploadDir);
			if(!Files.exists(path))
				Files.createDirectories(path);

			imageName = UUID.randomUUID() + productRequest.getImage().getOriginalFilename();
			Files.copy(productRequest.getImage().getInputStream(), path.resolve(imageName), StandardCopyOption.REPLACE_EXISTING);
		}
		
		if(product == null)
			product = new Product();
		
		if(imageName != null)
			product.setImage(imageName);
		
		product.setCalories(productRequest.getCalories());
		product.setPrice(productRequest.getPrice());
		product.setIngredients(productRequest.getIngredients());
		product.setName(productRequest.getItemName());
		product.setStatus(product == null || product.isStatus());
		product.setCategory(categoryService.findById(productRequest.getCategoryId()));
		
		return  productRepo.save(product);
	}

	@Transactional
	@Override
	public void deleteProduct(int productId) throws ProductException {
		if(!productRepo.existsById(productId))
			throw new ProductException("Product with id " + productId + " doesnt exists");

		productRepo.deleteById(productId);
	}

	@Transactional
	@Override
	public void disableProduct(int productId) throws ProductException {
		 Product product = findById(productId);
		 if(product.isStatus()){
			 product.setStatus(false);
			 productRepo.save(product);
		 }
	}

	@Transactional
	@Override
	public void enableProduct(int productId) throws ProductException {
		Product product = findById(productId);
		if(!product.isStatus()){
			product.setStatus(false);
			productRepo.save(product);
		}
	}

	@Override
	public Page<Product> findAllProducts(PageRequest pageRequest) {
		return productRepo.findAll(pageRequest);
	}

	@Override
	public Product findById(int productId) throws ProductException {
		return  productRepo.findById(productId)
					.orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " not found!"));
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
