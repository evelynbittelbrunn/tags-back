package com.api.tags.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.api.tags.auth.AuthorizationController;
import com.api.tags.auth.definition.dto.AuthenticationDTO;
import com.api.tags.auth.definition.dto.ResgisterDTO;
import com.api.tags.infra.security.TokenService;
import com.api.tags.user.definition.RoleEnum;
import com.api.tags.user.definition.UserModel;
import com.api.tags.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class AuthorizationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository repository;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthorizationController authorizationController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorizationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testLogin_Success() throws Exception {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("user@example.com", "password");
        UserModel user = new UserModel("user@example.com", "User Name", "encryptedPassword", RoleEnum.USER);
        user.setId("userId");
        String token = "mockToken";

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(tokenService.generateToken(user)).thenReturn(token);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.userId").value("userId"));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService, times(1)).generateToken(user);
    }

    @Test
    public void testRegister_Success() throws Exception {
        ResgisterDTO registerDTO = new ResgisterDTO("user@example.com", "User Name", "password", RoleEnum.USER);
        when(repository.findByEmail(registerDTO.getEmail())).thenReturn(null);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isOk());

        verify(repository, times(1)).findByEmail(registerDTO.getEmail());
        verify(repository, times(1)).save(any(UserModel.class));
    }

    @Test
    public void testRegister_EmailAlreadyExists() throws Exception {
        ResgisterDTO registerDTO = new ResgisterDTO("user@example.com", "User Name", "password", RoleEnum.USER);
        UserModel existingUser = new UserModel("user@example.com", "Existing User", "encryptedPassword", RoleEnum.USER);

        when(repository.findByEmail(registerDTO.getEmail())).thenReturn(existingUser);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isBadRequest());

        verify(repository, times(1)).findByEmail(registerDTO.getEmail());
        verify(repository, never()).save(any(UserModel.class));
    }

    @Test
    public void testValidateToken_Success() throws Exception {
        String token = "Bearer mockToken";
        String subject = "userId";

        when(tokenService.validateToken("mockToken")).thenReturn(subject);

        mockMvc.perform(get("/auth/validate-token")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().string("Token is valid"));

        verify(tokenService, times(1)).validateToken("mockToken");
    }

    @Test
    public void testValidateToken_InvalidToken() throws Exception {
        String token = "Bearer invalidToken";

        when(tokenService.validateToken("invalidToken")).thenThrow(new RuntimeException("Invalid token"));

        mockMvc.perform(get("/auth/validate-token")
                .header("Authorization", token))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Token is invalid"));

        verify(tokenService, times(1)).validateToken("invalidToken");
    }
}
