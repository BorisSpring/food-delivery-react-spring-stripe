package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	Category findByName(String name);
}
