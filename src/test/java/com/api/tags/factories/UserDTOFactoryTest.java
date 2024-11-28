package com.api.tags.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.tags.user.definition.UserModel;
import com.api.tags.user.definition.dto.UserPostDTO;
import com.api.tags.user.factory.UserDTOFactory;

public class UserDTOFactoryTest {

    private UserDTOFactory userDTOFactory;

    @BeforeEach
    public void setup() {
        userDTOFactory = new UserDTOFactory();
    }

    @Test
    public void testCreateUserPostDTO_WithProfilePicture() {
        // Criar um mock de UserModel com uma foto de perfil
        byte[] mockProfilePicture = "mockPictureData".getBytes();
        UserModel mockUser = new UserModel();
        mockUser.setId("userId");
        mockUser.setName("John Doe");
        mockUser.setProfilePicture(mockProfilePicture);

        // Chamar o método a ser testado
        UserPostDTO userPostDTO = userDTOFactory.createUserPostDTO(mockUser);

        // Verificar os resultados
        assertNotNull(userPostDTO);
        assertEquals("userId", userPostDTO.getId());
        assertEquals("John Doe", userPostDTO.getName());
        assertEquals(Base64.getEncoder().encodeToString(mockProfilePicture), userPostDTO.getProfilePicture());
    }

    @Test
    public void testCreateUserPostDTO_WithoutProfilePicture() {
        // Criar um mock de UserModel sem uma foto de perfil
        UserModel mockUser = new UserModel();
        mockUser.setId("userId");
        mockUser.setName("Jane Doe");
        mockUser.setProfilePicture(null);

        // Chamar o método a ser testado
        UserPostDTO userPostDTO = userDTOFactory.createUserPostDTO(mockUser);

        // Verificar os resultados
        assertNotNull(userPostDTO);
        assertEquals("userId", userPostDTO.getId());
        assertEquals("Jane Doe", userPostDTO.getName());
        assertEquals(null, userPostDTO.getProfilePicture());
    }
}
