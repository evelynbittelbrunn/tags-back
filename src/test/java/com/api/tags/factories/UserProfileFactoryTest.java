package com.api.tags.factories;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Base64;

import org.junit.jupiter.api.Test;

import com.api.tags.user.definition.UserModel;
import com.api.tags.user.definition.dto.UserProfileEditDTO;
import com.api.tags.user.factory.UserProfileFactory;

public class UserProfileFactoryTest {

    @Test
    public void testUpdateUserProfile_WithAllFields() {
        // Mock do UserModel existente
        UserModel mockUser = new UserModel();
        mockUser.setId("userId");
        mockUser.setName("Original Name");
        mockUser.setBio("Original Bio");
        mockUser.setProfilePicture("OriginalPicture".getBytes());

        // Mock do UserProfileEditDTO com novos valores
        String newBase64Picture = Base64.getEncoder().encodeToString("NewPictureData".getBytes());
        UserProfileEditDTO mockUserProfileDTO = new UserProfileEditDTO(
                "Updated Name",
                "Updated Bio",
                newBase64Picture
        );

        // Chamar o método a ser testado
        UserModel updatedUser = UserProfileFactory.updateUserProfile(mockUser, mockUserProfileDTO);

        // Verificar os resultados
        assertNotNull(updatedUser);
        assertEquals("Updated Name", updatedUser.getName());
        assertEquals("Updated Bio", updatedUser.getBio());
        assertArrayEquals("NewPictureData".getBytes(), updatedUser.getProfilePicture());
    }

    @Test
    public void testUpdateUserProfile_WithPartialFields() {
        // Mock do UserModel existente
        UserModel mockUser = new UserModel();
        mockUser.setId("userId");
        mockUser.setName("Original Name");
        mockUser.setBio("Original Bio");
        mockUser.setProfilePicture("OriginalPicture".getBytes());

        // Mock do UserProfileEditDTO com apenas bio alterado
        UserProfileEditDTO mockUserProfileDTO = new UserProfileEditDTO(
                null,
                "Updated Bio",
                null
        );

        // Chamar o método a ser testado
        UserModel updatedUser = UserProfileFactory.updateUserProfile(mockUser, mockUserProfileDTO);

        // Verificar os resultados
        assertNotNull(updatedUser);
        assertEquals("Original Name", updatedUser.getName());
        assertEquals("Updated Bio", updatedUser.getBio());
        assertArrayEquals("OriginalPicture".getBytes(), updatedUser.getProfilePicture());
    }

    @Test
    public void testUpdateUserProfile_WithEmptyFields() {
        // Mock do UserModel existente
        UserModel mockUser = new UserModel();
        mockUser.setId("userId");
        mockUser.setName("Original Name");
        mockUser.setBio("Original Bio");
        mockUser.setProfilePicture("OriginalPicture".getBytes());

        // Mock do UserProfileEditDTO com campos vazios
        UserProfileEditDTO mockUserProfileDTO = new UserProfileEditDTO(
                "",
                "",
                ""
        );

        // Chamar o método a ser testado
        UserModel updatedUser = UserProfileFactory.updateUserProfile(mockUser, mockUserProfileDTO);

        // Verificar os resultados
        assertNotNull(updatedUser);
        assertEquals("Original Name", updatedUser.getName());
        assertEquals("Original Bio", updatedUser.getBio());
        assertArrayEquals("OriginalPicture".getBytes(), updatedUser.getProfilePicture());
    }

    @Test
    public void testUpdateUserProfile_WithNullFields() {
        // Mock do UserModel existente
        UserModel mockUser = new UserModel();
        mockUser.setId("userId");
        mockUser.setName("Original Name");
        mockUser.setBio("Original Bio");
        mockUser.setProfilePicture("OriginalPicture".getBytes());

        // Mock do UserProfileEditDTO com campos nulos
        UserProfileEditDTO mockUserProfileDTO = new UserProfileEditDTO(
                null,
                null,
                null
        );

        // Chamar o método a ser testado
        UserModel updatedUser = UserProfileFactory.updateUserProfile(mockUser, mockUserProfileDTO);

        // Verificar os resultados
        assertNotNull(updatedUser);
        assertEquals("Original Name", updatedUser.getName());
        assertEquals("Original Bio", updatedUser.getBio());
        assertArrayEquals("OriginalPicture".getBytes(), updatedUser.getProfilePicture());
    }
}
