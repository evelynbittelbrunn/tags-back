package com.api.tags.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.tags.category.definition.CategoryModel;
import com.api.tags.category.definition.dto.CategoryWithSavedStatusDTO;

@RestController
@RequestMapping("/tags/categories")
public class CategoryController {
	@Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryModel> getAllCategories() {
        return categoryService.findAllCategories();
    }
    
    @GetMapping("/user-categories/{userId}")
    public List<CategoryWithSavedStatusDTO> getUserCategories(@PathVariable String userId) {
    	return categoryService.findAllCategoriesByUserId(userId);
    }
    
}
