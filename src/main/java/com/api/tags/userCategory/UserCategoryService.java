package com.api.tags.userCategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.tags.userCategory.definition.UserCategoryModel;
import com.api.tags.userCategory.repository.UserCategoryRepository;

@Service
public class UserCategoryService {
	@Autowired
    private UserCategoryRepository userCategoryRepository;

    public List<UserCategoryModel> getCategoriesByUserId(String userId) {
        return userCategoryRepository.findByUserId(userId);
    }
}
