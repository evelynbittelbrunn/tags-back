package com.api.tags.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.tags.category.definition.CategoryModel;
import com.api.tags.category.repository.CategoryRepository;
import com.api.tags.user.definition.UserModel;
import com.api.tags.user.repository.UserRepository;
import com.api.tags.userCategory.UserCategoryService;
import com.api.tags.userCategory.definition.UserCategoryId;
import com.api.tags.userCategory.definition.UserCategoryModel;
import com.api.tags.userCategory.repository.UserCategoryRepository;

@ExtendWith(MockitoExtension.class)
class UserCategoryServiceTest {

    @Mock
    private UserCategoryRepository userCategoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private UserCategoryService userCategoryService;

    private UserModel user;
    private CategoryModel category1;
    private CategoryModel category2;
    private UserCategoryModel userCategory1;
    private UserCategoryModel userCategory2;

    @BeforeEach
    void setUp() {
        user = new UserModel();
        user.setId("user123");
        user.setName("John Doe");

        category1 = new CategoryModel();
        category1.setId("category1");

        category2 = new CategoryModel();
        category2.setId("category2");

        userCategory1 = new UserCategoryModel(new UserCategoryId("user123", "category1"), user, category1);
        userCategory2 = new UserCategoryModel(new UserCategoryId("user123", "category2"), user, category2);
    }

    @Test
    void testGetCategoriesByUserId() {
        // Mocka o comportamento do repositório
        when(userCategoryRepository.findByUserId("user123")).thenReturn(List.of(userCategory1, userCategory2));

        // Chama o método do serviço
        List<UserCategoryModel> result = userCategoryService.getCategoriesByUserId("user123");

        // Verifica os resultados
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(userCategory1));
        assertTrue(result.contains(userCategory2));

        // Verifica a interação com o repositório
        verify(userCategoryRepository, times(1)).findByUserId("user123");
    }

    @Test
    void testSaveUserCategories() {
        // Mocka o comportamento do repositório de usuário
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));

        // Mocka o comportamento do repositório de categorias existentes
        when(userCategoryRepository.findByUserId("user123")).thenReturn(List.of(userCategory1));

        // Mocka a busca de categorias
        when(categoryRepository.findById("category2")).thenReturn(Optional.of(category2));

        // Configura os IDs de categorias a serem salvas
        List<String> categoryIds = List.of("category2");

        // Chama o método do serviço
        userCategoryService.saveUserCategories("user123", categoryIds);

        // Verifica a exclusão da categoria removida
        verify(userCategoryRepository, times(1)).deleteAll(argThat(categories -> {
            List<UserCategoryModel> categoryList = new ArrayList<>();
            categories.forEach(categoryList::add); // Converte Iterable para List
            return categoryList.stream().map(UserCategoryModel::getCategory).anyMatch(cat -> cat.equals(category1));
        }));

        // Verifica a adição de novas categorias
        verify(userCategoryRepository, times(1)).saveAll(argThat(categories -> {
            List<UserCategoryModel> categoryList = new ArrayList<>();
            categories.forEach(categoryList::add); // Converte Iterable para List
            return categoryList.stream().map(UserCategoryModel::getCategory).anyMatch(cat -> cat.equals(category2));
        }));
    }

    @Test
    void testSaveUserCategoriesThrowsWhenUserNotFound() {
        // Mocka o comportamento do repositório de usuário para retornar vazio
        when(userRepository.findById("user123")).thenReturn(Optional.empty());

        // Configura os IDs de categorias
        List<String> categoryIds = List.of("category1", "category2");

        // Verifica a exceção lançada
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userCategoryService.saveUserCategories("user123", categoryIds);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    void testSaveUserCategoriesThrowsWhenCategoryNotFound() {
        // Mocka o comportamento do repositório de usuário
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));

        // Mocka o comportamento do repositório de categorias
        when(categoryRepository.findById("category1")).thenReturn(Optional.empty());

        // Configura os IDs de categorias
        List<String> categoryIds = List.of("category1");

        // Verifica a exceção lançada
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userCategoryService.saveUserCategories("user123", categoryIds);
        });

        assertEquals("Categoria não encontrada", exception.getMessage());
    }
}
