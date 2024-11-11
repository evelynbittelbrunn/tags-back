package com.api.tags.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.api.tags.category.CategoryService;
import com.api.tags.category.definition.CategoryModel;
import com.api.tags.category.definition.dto.CategoryWithSavedStatusDTO;
import com.api.tags.category.repository.CategoryRepository;

public class CategoryServiceTest {
	@Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllCategories() {

        List<CategoryModel> mockCategories = Arrays.asList(
                new CategoryModel("1", "Category 1"),
                new CategoryModel("2", "Category 2")
        );
        when(categoryRepository.findAll()).thenReturn(mockCategories);

        List<CategoryModel> categories = categoryService.findAllCategories();

        assertEquals(2, categories.size());
        assertEquals("Category 1", categories.get(0).getDescription());
        assertEquals("Category 2", categories.get(1).getDescription());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testFindAllCategoriesWhenEmpty() {
        when(categoryRepository.findAll()).thenReturn(List.of());

        List<CategoryModel> categories = categoryService.findAllCategories();

        assertEquals(0, categories.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testFindAllCategoriesByUserId() {

        String userId = "user-123";
        List<CategoryWithSavedStatusDTO> mockCategoriesWithStatus = Arrays.asList(
                new CategoryWithSavedStatusDTO("1", "Category 1", true),
                new CategoryWithSavedStatusDTO("2", "Category 2", false)
        );
        when(categoryRepository.findAllWithSavedStatus(userId)).thenReturn(mockCategoriesWithStatus);

        List<CategoryWithSavedStatusDTO> categoriesWithStatus = categoryService.findAllCategoriesByUserId(userId);

        assertEquals(2, categoriesWithStatus.size());
        assertEquals("Category 1", categoriesWithStatus.get(0).getDescription());
        assertTrue(categoriesWithStatus.get(0).isSavedByUser());
        assertEquals("Category 2", categoriesWithStatus.get(1).getDescription());
        assertFalse(categoriesWithStatus.get(1).isSavedByUser());
        verify(categoryRepository, times(1)).findAllWithSavedStatus(userId);
    }

    @Test
    void testFindAllCategoriesByUserIdWhenEmpty() {
        String userId = "user-123";
        when(categoryRepository.findAllWithSavedStatus(userId)).thenReturn(List.of());

        List<CategoryWithSavedStatusDTO> categoriesWithStatus = categoryService.findAllCategoriesByUserId(userId);

        assertEquals(0, categoriesWithStatus.size());
        verify(categoryRepository, times(1)).findAllWithSavedStatus(userId);
    }
}
