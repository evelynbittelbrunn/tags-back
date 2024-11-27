package com.api.tags.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.api.tags.auth.AuthorizationService;
import com.api.tags.auth.definition.dto.AuthenticationDTO;
import com.api.tags.auth.definition.dto.LoginResponseDTO;
import com.api.tags.auth.definition.dto.ResgisterDTO;
import com.api.tags.user.definition.RoleEnum;
import com.api.tags.user.definition.UserModel;
import com.api.tags.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthorizationService authorizationService;

    private UserModel user;

    @BeforeEach
    void setUp() {
        user = new UserModel();
        user.setId("user123");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setName("John Doe");
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Mocka o repositório para retornar um usuário válido
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        // Chama o método do serviço
        UserDetails result = authorizationService.loadUserByUsername("test@example.com");

        // Verifica o resultado
        assertNotNull(result);
        assertEquals("test@example.com", result.getUsername());
        assertEquals("password", result.getPassword());

        // Verifica se o repositório foi chamado corretamente
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testAuthenticationWithDTO() {
        // Mocka o repositório para retornar um usuário válido
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        // Simula o DTO de autenticação
        AuthenticationDTO authDTO = new AuthenticationDTO();
        authDTO.setEmail("test@example.com");
        authDTO.setPassword("password");

        // Chama o método do serviço
        UserDetails result = authorizationService.loadUserByUsername(authDTO.getEmail());

        // Verifica o resultado
        assertNotNull(result);
        assertEquals(authDTO.getEmail(), result.getUsername());
        assertEquals(authDTO.getPassword(), user.getPassword()); // Senha deve coincidir com o mock

        // Verifica se o repositório foi chamado corretamente
        verify(userRepository, times(1)).findByEmail(authDTO.getEmail());
    }

    @Test
    void testRegisterNewUser() {
        // Simula o DTO de registro
        ResgisterDTO registerDTO = new ResgisterDTO();
        registerDTO.setEmail("newuser@example.com");
        registerDTO.setName("New User");
        registerDTO.setPassword("newpassword");
        registerDTO.setRole(RoleEnum.USER);

        // Simula a persistência no repositório
        when(userRepository.save(any(UserModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Cria o modelo esperado
        UserModel expectedUser = new UserModel(
            registerDTO.getEmail(),
            registerDTO.getName(),
            registerDTO.getPassword(),
            registerDTO.getRole()
        );

        // Salva o usuário
        UserModel savedUser = userRepository.save(expectedUser);

        // Validações
        assertNotNull(savedUser);
        assertEquals(registerDTO.getEmail(), savedUser.getEmail());
        assertEquals(registerDTO.getName(), savedUser.getName());
        assertEquals(registerDTO.getRole(), savedUser.getRole());

        // Verifica se o repositório foi chamado corretamente
        verify(userRepository, times(1)).save(any(UserModel.class));
    }
}
