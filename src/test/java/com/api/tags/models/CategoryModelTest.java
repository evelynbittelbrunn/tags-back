package com.api.tags.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.api.tags.category.definition.CategoryModel;

public class CategoryModelTest {
	@Test
    void testCategoryConstructor() {
        CategoryModel category = new CategoryModel("1", "Test Category");
        assertNotNull(category);
        assertEquals("1", category.getId());
        assertEquals("Test Category", category.getDescription());
    }

    @Test
    void testEqualsAndHashCode() {
        CategoryModel category1 = new CategoryModel("1", "Category 1");
        CategoryModel category2 = new CategoryModel("1", "Category 1");
        CategoryModel category3 = new CategoryModel("2", "Category 2");

        assertEquals(category1, category2);
        assertNotEquals(category1, category3); 
        assertEquals(category1.hashCode(), category2.hashCode());
    }

    @Test
    void testNoArgsConstructor() {
        CategoryModel category = new CategoryModel();
        assertNull(category.getId());
        assertNull(category.getDescription());
    }
}
