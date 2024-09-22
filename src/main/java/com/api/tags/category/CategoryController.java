package com.api.tags.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.tags.category.definition.CategoryModel;

@RestController
@RequestMapping("/tags/categories")
public class CategoryController {
	@Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryModel> getAllCategories() {
        return categoryService.findAllCategories();
    }
}
