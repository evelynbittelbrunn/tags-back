package com.api.tags.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.tags.category.definition.CategoryModel;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, String> {

}
