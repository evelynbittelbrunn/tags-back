package com.api.tags.models;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.api.tags.user.definition.RoleEnum;
import com.api.tags.user.definition.UserModel;

public class UserModelTest {

    @Test
    public void testConstructorAndGetters() {
        // Arrange
        String id = "user123";
        String email = "user@example.com";
        String name = "John Doe";
        String password = "securePassword";
        RoleEnum role = RoleEnum.USER;
        byte[] profilePicture = "image".getBytes();
        String bio = "This is a bio";
        LocalDateTime createdAt = LocalDateTime.now();

        // Act
        UserModel user = new UserModel(id, email, name, password, role, profilePicture, bio, createdAt, null, null);

        // Assert
        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(name, user.getName());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
        assertArrayEquals(profilePicture, user.getProfilePicture());
        assertEquals(bio, user.getBio());
        assertEquals(createdAt, user.getCreatedAt());
    }

    @Test
    public void testRoleAuthorities_User() {
        // Arrange
        UserModel normalUser = new UserModel("user@example.com", "User", "password", RoleEnum.USER);

        // Act
        var authorities = normalUser.getAuthorities();

        // Assert
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    public void testUsername() {
        // Arrange
        String email = "user@example.com";
        UserModel user = new UserModel(email, "User", "password", RoleEnum.USER);

        // Act
        String username = user.getUsername();

        // Assert
        assertEquals(email, username);
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        UserModel user1 = new UserModel("user123", "user@example.com", "User", "password", RoleEnum.USER, null, null, null, null, null);
        UserModel user2 = new UserModel("user123", "another@example.com", "Another User", "password123", RoleEnum.USER, null, null, null, null, null);

        // Act & Assert
        assertEquals(user1, user2); // Same ID
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testToString() {
        // Arrange
        UserModel user = new UserModel("user123", "user@example.com", "User", "password", RoleEnum.USER, null, "This is a bio", LocalDateTime.now(), null, null);

        // Act
        String toString = user.toString();

        // Assert
        assertTrue(toString.contains("id=user123"));
        assertTrue(toString.contains("email=user@example.com"));
        assertTrue(toString.contains("name=User"));
        assertTrue(toString.contains("role=USER"));
        assertTrue(toString.contains("bio=This is a bio"));
    }
}
