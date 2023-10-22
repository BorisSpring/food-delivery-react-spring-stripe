package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.entity.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer>{

	public Authority findByName(String name);
}
