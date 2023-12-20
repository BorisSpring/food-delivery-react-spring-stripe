package com.main.service;

import java.util.List;

import com.main.entity.Category;
import com.main.exceptions.CategoryException;
import com.main.requests.CategoryRequest;

public interface CategoryService {

	 Category addCategory(CategoryRequest req) throws CategoryException;
	
	 void deleteCategory(int categoryId)throws CategoryException;
	
	 Category findById(int categoryId)throws CategoryException;
	
	 List<Category> findAll();
}
