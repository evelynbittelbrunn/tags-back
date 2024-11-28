package com.api.tags.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.api.tags.userCategory.definition.UserCategoryId;

public class UserCategoryIdTest {

    @Test
    public void testConstructorAndGetters() {
        // Arrange
        String userId = "user123";
        String categoryId = "category456";

        // Act
        UserCategoryId userCategoryId = new UserCategoryId(userId, categoryId);

        // Assert
        assertNotNull(userCategoryId);
        assertEquals(userId, userCategoryId.getUserId());
        assertEquals(categoryId, userCategoryId.getCategoryId());
    }

    @Test
    public void testSetters() {
        // Arrange
        UserCategoryId userCategoryId = new UserCategoryId();
        String userId = "user123";
        String categoryId = "category456";

        // Act
        userCategoryId.setUserId(userId);
        userCategoryId.setCategoryId(categoryId);

        // Assert
        assertEquals(userId, userCategoryId.getUserId());
        assertEquals(categoryId, userCategoryId.getCategoryId());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        UserCategoryId id1 = new UserCategoryId("user123", "category456");
        UserCategoryId id2 = new UserCategoryId("user123", "category456");
        UserCategoryId id3 = new UserCategoryId("user789", "category456");

        // Act & Assert
        assertEquals(id1, id2);
        assertNotEquals(id1, id3);
        assertEquals(id1.hashCode(), id2.hashCode());
        assertNotEquals(id1.hashCode(), id3.hashCode());
    }

    @Test
    public void testToString() {
        // Arrange
        UserCategoryId userCategoryId = new UserCategoryId("user123", "category456");

        // Act
        String toString = userCategoryId.toString();

        // Assert
        assertTrue(toString.contains("userId=user123"));
        assertTrue(toString.contains("categoryId=category456"));
    }
}
