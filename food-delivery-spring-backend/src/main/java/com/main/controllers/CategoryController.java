package com.main.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.entity.Category;
import com.main.exceptions.CategoryException;
import com.main.requests.CategoryRequest;
import com.main.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	
	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public ResponseEntity<List<Category>> findAllCategoriesHandler(){
		
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll());
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<Category> findCategoryByIdHandler(@PathVariable int categoryId) throws CategoryException{
		
		
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.findById(categoryId));
	}
	
	@PostMapping
	public ResponseEntity<Category> addCategoryOrUpdateHandler(@RequestBody CategoryRequest req) throws CategoryException{
		
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.addCategory(req));
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<Boolean> deleteCategoryHandler(@PathVariable int categoryId) throws CategoryException{
		
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.deleteCategory(categoryId));
	}
	
}
