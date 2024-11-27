package com.api.tags.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.api.tags.user.definition.dto.UserDTO;

class UserDTOTest {

    @Test
    void testUserDTOGetterAndSetter() {
        // Criação de uma instância do UserDTO
        UserDTO userDTO = new UserDTO();

        // Configurando os valores
        userDTO.setId("123");
        userDTO.setName("Test User");
        userDTO.setProfilePicture("https://example.com/pic.jpg");

        // Verificando se os valores foram configurados corretamente
        assertEquals("123", userDTO.getId());
        assertEquals("Test User", userDTO.getName());
        assertEquals("https://example.com/pic.jpg", userDTO.getProfilePicture());
    }

    @Test
    void testEqualsAndHashCode() {
        // Criando duas instâncias com os mesmos dados
        UserDTO user1 = new UserDTO();
        user1.setId("123");
        user1.setName("Test User");
        user1.setProfilePicture("https://example.com/pic.jpg");

        UserDTO user2 = new UserDTO();
        user2.setId("123");
        user2.setName("Test User");
        user2.setProfilePicture("https://example.com/pic.jpg");

        // Verificando se equals e hashCode são consistentes
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testToString() {
        // Configurando os dados
        UserDTO userDTO = new UserDTO();
        userDTO.setId("123");
        userDTO.setName("Test User");
        userDTO.setProfilePicture("https://example.com/pic.jpg");

        // Verificando se toString retorna algo que inclui os valores configurados
        String toString = userDTO.toString();
        assertTrue(toString.contains("123"));
        assertTrue(toString.contains("Test User"));
        assertTrue(toString.contains("https://example.com/pic.jpg"));
    }
}
