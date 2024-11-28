package com.api.tags.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.api.tags.category.definition.CategoryModel;
import com.api.tags.user.definition.UserModel;
import com.api.tags.userCategory.UserCategoryController;
import com.api.tags.userCategory.UserCategoryService;
import com.api.tags.userCategory.definition.UserCategoryId;
import com.api.tags.userCategory.definition.UserCategoryModel;
import com.api.tags.userCategory.definition.dto.UserCategoryRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class UserCategoryControllerTest {

    @Mock
    private UserCategoryService userCategoryService;

    @InjectMocks
    private UserCategoryController userCategoryController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userCategoryController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetUserCategories_Success() throws Exception {
        String userId = "user123";

        UserModel user = new UserModel();
        user.setId(userId);

        CategoryModel category1 = new CategoryModel();
        category1.setId("category1");
        category1.setDescription("Category 1");

        CategoryModel category2 = new CategoryModel();
        category2.setId("category2");
        category2.setDescription("Category 2");

        UserCategoryModel userCategory1 = new UserCategoryModel(new UserCategoryId(userId, "category1"), user, category1);
        UserCategoryModel userCategory2 = new UserCategoryModel(new UserCategoryId(userId, "category2"), user, category2);

        List<UserCategoryModel> categories = Arrays.asList(userCategory1, userCategory2);

        when(userCategoryService.getCategoriesByUserId(userId)).thenReturn(categories);

        mockMvc.perform(get("/tags/user-categories/{userId}", userId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id.userId").value("user123"))
            .andExpect(jsonPath("$[0].id.categoryId").value("category1"))
            .andExpect(jsonPath("$[0].category.description").value("Category 1"))
            .andExpect(jsonPath("$[1].id.userId").value("user123"))
            .andExpect(jsonPath("$[1].id.categoryId").value("category2"))
            .andExpect(jsonPath("$[1].category.description").value("Category 2"));

        verify(userCategoryService, times(1)).getCategoriesByUserId(userId);
    }

    @Test
    public void testSaveUserCategories_Success() throws Exception {
        UserCategoryRequestDTO requestDTO = new UserCategoryRequestDTO("user123", Arrays.asList("category1", "category2"));

        doNothing().when(userCategoryService).saveUserCategories(requestDTO.getUserId(), requestDTO.getCategoryIds());

        mockMvc.perform(post("/tags/user-categories/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isOk())
            .andExpect(content().string("Salvo com sucesso"));

        verify(userCategoryService, times(1)).saveUserCategories(requestDTO.getUserId(), requestDTO.getCategoryIds());
    }
}
