package com.api.tags.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.tags.category.definition.CategoryModel;

public interface CategoryRepository extends JpaRepository<CategoryModel, String> {

}
