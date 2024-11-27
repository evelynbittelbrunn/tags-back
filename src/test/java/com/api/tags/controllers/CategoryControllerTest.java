package com.api.tags.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.api.tags.category.CategoryController;
import com.api.tags.category.CategoryService;
import com.api.tags.category.definition.CategoryModel;
import com.api.tags.category.definition.dto.CategoryWithSavedStatusDTO;
import com.api.tags.infra.security.TokenService;
import com.api.tags.user.repository.UserRepository;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserRepository userRepository;

    private CategoryModel category1;
    private CategoryModel category2;

    private CategoryWithSavedStatusDTO userCategory1;
    private CategoryWithSavedStatusDTO userCategory2;

    @BeforeEach
    void setUp() {
        category1 = new CategoryModel("category1", "Category 1");
        category2 = new CategoryModel("category2", "Category 2");

        userCategory1 = new CategoryWithSavedStatusDTO("category1", "Category 1", true);
        userCategory2 = new CategoryWithSavedStatusDTO("category2", "Category 2", false);
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testGetAllCategories() throws Exception {
        when(categoryService.findAllCategories()).thenReturn(List.of(category1, category2));

        mockMvc.perform(get("/tags/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("category1"))
                .andExpect(jsonPath("$[0].description").value("Category 1"))
                .andExpect(jsonPath("$[1].id").value("category2"))
                .andExpect(jsonPath("$[1].description").value("Category 2"));

        verify(categoryService, times(1)).findAllCategories();
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testGetUserCategories() throws Exception {
        when(categoryService.findAllCategoriesByUserId("user123")).thenReturn(List.of(userCategory1, userCategory2));

        mockMvc.perform(get("/tags/categories/user-categories/user123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("category1"))
                .andExpect(jsonPath("$[0].description").value("Category 1"))
                .andExpect(jsonPath("$[0].savedByUser").value(true))
                .andExpect(jsonPath("$[1].id").value("category2"))
                .andExpect(jsonPath("$[1].description").value("Category 2"))
                .andExpect(jsonPath("$[1].savedByUser").value(false));

        verify(categoryService, times(1)).findAllCategoriesByUserId("user123");
    }
}
