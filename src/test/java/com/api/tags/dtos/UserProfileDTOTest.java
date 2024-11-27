package com.api.tags.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.api.tags.user.definition.dto.UserProfileDTO;

class UserProfileDTOTest {

    @Test
    void testUserProfileDTOGetterAndSetter() {
        // Criação de uma instância do UserProfileDTO
        UserProfileDTO userProfileDTO = new UserProfileDTO(
                "Test User",
                "This is a bio",
                "https://example.com/profile.jpg",
                true,
                100,
                50
        );

        // Verificando se os valores configurados no construtor estão corretos
        assertEquals("Test User", userProfileDTO.getName());
        assertEquals("This is a bio", userProfileDTO.getBio());
        assertEquals("https://example.com/profile.jpg", userProfileDTO.getProfilePicture());
        assertTrue(userProfileDTO.isFollowing());
        assertEquals(100, userProfileDTO.getFollowersCount());
        assertEquals(50, userProfileDTO.getFollowingCount());
    }

    @Test
    void testEqualsAndHashCode() {
        // Criando duas instâncias com os mesmos dados
        UserProfileDTO user1 = new UserProfileDTO(
                "Test User",
                "This is a bio",
                "https://example.com/profile.jpg",
                true,
                100,
                50
        );

        UserProfileDTO user2 = new UserProfileDTO(
                "Test User",
                "This is a bio",
                "https://example.com/profile.jpg",
                true,
                100,
                50
        );

        // Verificando se equals e hashCode são consistentes
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testToString() {
        // Configurando os dados
        UserProfileDTO userProfileDTO = new UserProfileDTO(
                "Test User",
                "This is a bio",
                "https://example.com/profile.jpg",
                true,
                100,
                50
        );

        // Verificando se toString retorna algo que inclui os valores configurados
        String toString = userProfileDTO.toString();
        assertTrue(toString.contains("Test User"));
        assertTrue(toString.contains("This is a bio"));
        assertTrue(toString.contains("https://example.com/profile.jpg"));
        assertTrue(toString.contains("100"));
        assertTrue(toString.contains("50"));
    }

    @Test
    void testAllArgsConstructor() {
        // Criando o objeto usando o construtor com todos os argumentos
        UserProfileDTO userProfileDTO = new UserProfileDTO(
                "Test User",
                "This is a bio",
                "https://example.com/profile.jpg",
                true,
                100,
                50
        );

        // Verificando se os valores são configurados corretamente
        assertEquals("Test User", userProfileDTO.getName());
        assertEquals("This is a bio", userProfileDTO.getBio());
        assertEquals("https://example.com/profile.jpg", userProfileDTO.getProfilePicture());
        assertTrue(userProfileDTO.isFollowing());
        assertEquals(100, userProfileDTO.getFollowersCount());
        assertEquals(50, userProfileDTO.getFollowingCount());
    }
}
