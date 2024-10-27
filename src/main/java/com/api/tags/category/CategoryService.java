package com.api.tags.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.tags.category.definition.CategoryModel;
import com.api.tags.category.definition.dto.CategoryWithSavedStatusDTO;
import com.api.tags.category.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
	@Autowired
    private CategoryRepository categoryRepository;
	
    public List<CategoryModel> findAllCategories() {
        return categoryRepository.findAll();
    }
    
    public List<CategoryWithSavedStatusDTO> findAllCategoriesByUserId(String userId) {
        return categoryRepository.findAllWithSavedStatus(userId);
    }
}
