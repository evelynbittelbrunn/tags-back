package com.api.tags.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.api.tags.category.definition.CategoryModel;
import com.api.tags.user.definition.RoleEnum;
import com.api.tags.user.definition.UserModel;
import com.api.tags.userCategory.definition.UserCategoryId;
import com.api.tags.userCategory.definition.UserCategoryModel;

public class UserCategoryModelTest {

    @Test
    public void testConstructorAndGetters() {
        // Arrange
        UserCategoryId userCategoryId = new UserCategoryId("user123", "category456");
        UserModel user = new UserModel("user123", "User Name", "password", RoleEnum.USER);
        CategoryModel category = new CategoryModel("category456", "Category Name");

        // Act
        UserCategoryModel userCategoryModel = new UserCategoryModel(userCategoryId, user, category);

        // Assert
        assertNotNull(userCategoryModel);
        assertEquals(userCategoryId, userCategoryModel.getId());
        assertEquals(user, userCategoryModel.getUser());
        assertEquals(category, userCategoryModel.getCategory());
    }

    @Test
    public void testSetters() {
        // Arrange
        UserCategoryModel userCategoryModel = new UserCategoryModel();
        UserCategoryId userCategoryId = new UserCategoryId("user123", "category456");
        UserModel user = new UserModel("user123", "User Name", "password", RoleEnum.USER);
        CategoryModel category = new CategoryModel("category456", "Category Name");

        // Act
        userCategoryModel.setId(userCategoryId);
        userCategoryModel.setUser(user);
        userCategoryModel.setCategory(category);

        // Assert
        assertEquals(userCategoryId, userCategoryModel.getId());
        assertEquals(user, userCategoryModel.getUser());
        assertEquals(category, userCategoryModel.getCategory());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        UserCategoryId id1 = new UserCategoryId("user123", "category456");
        UserCategoryId id2 = new UserCategoryId("user123", "category456");
        UserCategoryId id3 = new UserCategoryId("user789", "category456");

        UserModel user1 = new UserModel("user123", "User Name", "password", RoleEnum.USER);
        UserModel user2 = new UserModel("user789", "Other User", "password", RoleEnum.USER);

        CategoryModel category1 = new CategoryModel("category456", "Category Name");
        CategoryModel category2 = new CategoryModel("category789", "Other Category");

        UserCategoryModel model1 = new UserCategoryModel(id1, user1, category1);
        UserCategoryModel model2 = new UserCategoryModel(id2, user1, category1);
        UserCategoryModel model3 = new UserCategoryModel(id3, user2, category2);

        // Act & Assert
        assertEquals(model1, model2);
        assertNotEquals(model1, model3);
        assertEquals(model1.hashCode(), model2.hashCode());
        assertNotEquals(model1.hashCode(), model3.hashCode());
    }

    @Test
    public void testToString() {
        // Arrange
        UserCategoryId userCategoryId = new UserCategoryId("user123", "category456");
        UserModel user = new UserModel("user123", "User Name", "password", RoleEnum.USER);
        CategoryModel category = new CategoryModel("category456", "Category Name");

        UserCategoryModel userCategoryModel = new UserCategoryModel(userCategoryId, user, category);

        // Act
        String toString = userCategoryModel.toString();

        // Assert
        assertTrue(toString.contains("id=UserCategoryId(userId=user123, categoryId=category456)"));
        assertTrue(toString.contains("userId=user123"));
        assertTrue(toString.contains("categoryId=category456"));
    }

}
