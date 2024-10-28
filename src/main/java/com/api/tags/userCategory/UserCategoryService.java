package com.api.tags.userCategory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.tags.category.definition.CategoryModel;
import com.api.tags.category.repository.CategoryRepository;
import com.api.tags.user.definition.UserModel;
import com.api.tags.user.repository.UserRepository;
import com.api.tags.userCategory.definition.UserCategoryId;
import com.api.tags.userCategory.definition.UserCategoryModel;
import com.api.tags.userCategory.repository.UserCategoryRepository;

@Service
public class UserCategoryService {
	@Autowired
    private UserCategoryRepository userCategoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

    public List<UserCategoryModel> getCategoriesByUserId(String userId) {
        return userCategoryRepository.findByUserId(userId);
    }
    
    public void saveUserCategories(String userId, List<String> categoryIds) {
    	UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<UserCategoryModel> existingUserCategories = userCategoryRepository.findByUserId(userId);

        List<UserCategoryModel> categoriesToRemove = existingUserCategories.stream()
                .filter(userCategory -> !categoryIds.contains(userCategory.getCategory().getId()))
                .collect(Collectors.toList());
        userCategoryRepository.deleteAll(categoriesToRemove);

        List<UserCategoryModel> categoriesToAdd = categoryIds.stream()
                .filter(categoryId -> existingUserCategories.stream()
                        .noneMatch(existing -> existing.getCategory().getId().equals(categoryId)))
                .map(categoryId -> {
                    CategoryModel category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

                    UserCategoryId userCategoryId = new UserCategoryId(userId, categoryId);
                    return new UserCategoryModel(userCategoryId, user, category);
                })
                .collect(Collectors.toList());

        userCategoryRepository.saveAll(categoriesToAdd);
    }
}
