package com.api.tags.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.api.tags.user.definition.dto.UserPostDTO;

class UserPostDTOTest {

    @Test
    void testUserPostDTOGetterAndSetter() {
        // Criação de uma instância do UserPostDTO
        UserPostDTO userPostDTO = new UserPostDTO();

        // Configurando os valores
        userPostDTO.setId("123");
        userPostDTO.setName("Test User");
        userPostDTO.setProfilePicture("https://example.com/profile.jpg");

        // Verificando se os valores foram configurados corretamente
        assertEquals("123", userPostDTO.getId());
        assertEquals("Test User", userPostDTO.getName());
        assertEquals("https://example.com/profile.jpg", userPostDTO.getProfilePicture());
    }

    @Test
    void testEqualsAndHashCode() {
        // Criando duas instâncias com os mesmos dados
        UserPostDTO user1 = new UserPostDTO("123", "Test User", "https://example.com/profile.jpg");
        UserPostDTO user2 = new UserPostDTO("123", "Test User", "https://example.com/profile.jpg");

        // Verificando se equals e hashCode são consistentes
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testToString() {
        // Configurando os dados
        UserPostDTO userPostDTO = new UserPostDTO("123", "Test User", "https://example.com/profile.jpg");

        // Verificando se toString retorna algo que inclui os valores configurados
        String toString = userPostDTO.toString();
        assertTrue(toString.contains("123"));
        assertTrue(toString.contains("Test User"));
        assertTrue(toString.contains("https://example.com/profile.jpg"));
    }

    @Test
    void testAllArgsConstructor() {
        // Criando o objeto usando o construtor com todos os argumentos
        UserPostDTO userPostDTO = new UserPostDTO("123", "Test User", "https://example.com/profile.jpg");

        // Verificando se os valores são configurados corretamente
        assertEquals("123", userPostDTO.getId());
        assertEquals("Test User", userPostDTO.getName());
        assertEquals("https://example.com/profile.jpg", userPostDTO.getProfilePicture());
    }

    @Test
    void testNoArgsConstructor() {
        // Criando o objeto usando o construtor sem argumentos
        UserPostDTO userPostDTO = new UserPostDTO();

        // Verificando que os valores padrão são nulos
        assertNull(userPostDTO.getId());
        assertNull(userPostDTO.getName());
        assertNull(userPostDTO.getProfilePicture());
    }
}
