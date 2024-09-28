package com.api.tags.userCategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.tags.userCategory.definition.UserCategoryModel;

@RestController
@RequestMapping("/tags/user-categories")
public class UserCategoryController {
	@Autowired
    private UserCategoryService userCategoryService;

    @GetMapping("/{userId}")
    public List<UserCategoryModel> getUserCategories(@PathVariable String userId) {
        return userCategoryService.getCategoriesByUserId(userId);
    }
}
