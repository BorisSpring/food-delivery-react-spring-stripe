package com.main.controllers;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.main.entity.Category;
import com.main.exceptions.CategoryException;
import com.main.requests.CategoryRequest;
import com.main.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Validated
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping
	public ResponseEntity<List<Category>> findAllCategoriesHandler(){
		return ResponseEntity.ok(categoryService.findAll());
	}
	
	@GetMapping("/products")
	public ResponseEntity<Category> findCategoryByIdHandler(@Positive(message = "Cateogory Id Must be positive!")@RequestParam int categoryId) throws CategoryException{
		return ResponseEntity.ok(categoryService.findById(categoryId));
	}
	
	@PostMapping
	public ResponseEntity<Category> addCategoryOrUpdateHandler(@Valid @RequestBody CategoryRequest req) throws CategoryException{
		return ResponseEntity.ok(categoryService.addCategory(req));
	}
	
	@DeleteMapping()
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCategoryHandler(@Positive(message = "Category id must be positive!") @RequestParam int categoryId) throws CategoryException{
		categoryService.deleteCategory(categoryId);
	}
	
}
