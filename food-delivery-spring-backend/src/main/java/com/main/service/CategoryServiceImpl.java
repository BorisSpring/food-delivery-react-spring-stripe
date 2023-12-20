package com.main.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.main.entity.Category;
import com.main.exceptions.CategoryException;
import com.main.repository.CategoryRepository;
import com.main.requests.CategoryRequest;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepo;
	

	@Transactional
	@Override
	public Category addCategory(CategoryRequest req) throws CategoryException {
		if(categoryRepo.existsByName(req.getName()))
			throw new CategoryException("There is alerdy category with same name! Please chose another!");

		return  categoryRepo.save(Category.builder()
									.name(req.getName())
									.build());
	}

	@Transactional
	@Override
	public void deleteCategory(int categoryId) throws CategoryException {

			if(!categoryRepo.existsById(categoryId))
				throw new CategoryException("Category with id " + categoryId + " doesnt exists");
			
			categoryRepo.deleteById(categoryId);
	}

	@Override
	public Category findById(int categoryId) throws CategoryException {
		return categoryRepo.findById(categoryId).orElseThrow(() -> new ClassCastException("Category with id " + categoryId + " doesnt exists!"));
	}

	@Override
	public List<Category> findAll() {
		return categoryRepo.findAll();
	}

}
