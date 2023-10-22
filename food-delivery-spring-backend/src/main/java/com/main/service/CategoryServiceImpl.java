package com.main.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.main.entity.Category;
import com.main.exceptions.CategoryException;
import com.main.repository.CategoryRepository;
import com.main.requests.CategoryRequest;

import jakarta.transaction.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepo;
	
	
	
	public CategoryServiceImpl(CategoryRepository categoryRepo) {
		this.categoryRepo = categoryRepo;
	}

	@Transactional
	@Override
	public Category addCategory(CategoryRequest req) throws CategoryException {
		
		Category category = null;
		
		if(req.getCategoryId() != null) {
			category = findById(req.getCategoryId());
		}else {
			category = categoryRepo.findByName(req.getName());
		}
		
			if(req.getCategoryId() != null && category == null)
				throw new CategoryException("Category with name "  + req.getName() + " doesnt exists");
		
			if(category != null && req.getCategoryId() == null)
				throw new CategoryException("Category with name " + req.getName() + " alerdy exists");
		
			if(category == null) 
				category = new Category();
			
			
			category.setName(req.getName());
			category.setCreated(LocalDateTime.now());
			category =categoryRepo.save(category);
			
			if(category == null)
				throw new CategoryException("Fail to add category");
			
			return category;
		
	}

	@Transactional
	@Override
	public boolean deleteCategory(int categoryId) throws CategoryException {
			
			boolean existsById = categoryRepo.existsById(categoryId);
			
			if(!existsById)
				throw new CategoryException("Category with id " + categoryId + " doesnt exists");
			
			categoryRepo.deleteById(categoryId);
			return true;
			
	}

	@Override
	public Category findById(int categoryId) throws CategoryException {
		
		Optional<Category> opt = categoryRepo.findById(categoryId);
		
		if(!opt.isPresent())
			throw new CategoryException("Category doesnt exists");
		
		return opt.get();
	}

	@Override
	public List<Category> findAll() {
		return categoryRepo.findAll();
	}

}
