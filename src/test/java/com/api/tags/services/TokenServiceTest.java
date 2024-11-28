package com.api.tags.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.api.tags.infra.security.TokenService;
import com.api.tags.user.definition.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class TokenServiceTest {

    private TokenService tokenService;

    private final String secret = "testSecretKey";

    @BeforeEach
    public void setup() {
        tokenService = new TokenService(); // Instanciando o serviço
        ReflectionTestUtils.setField(tokenService, "secret", secret); // Injetando a chave secreta
    }

    @Test
    public void testGenerateToken_Success() {
        UserModel user = new UserModel("test@example.com", "Test User", "password", null);

        String token = tokenService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    public void testValidateToken_ValidToken() {
        String email = "test@example.com";

        // Gerar token manualmente para garantir que seja válido
        String token = JWT.create()
                .withIssuer("auth-api")
                .withSubject(email)
                .withExpiresAt(Date.from(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"))))
                .sign(Algorithm.HMAC256(secret));

        String subject = tokenService.validateToken(token);

        assertEquals(email, subject);
    }

    @Test
    public void testValidateToken_InvalidToken() {
        String invalidToken = "invalid.token.string";

        String subject = tokenService.validateToken(invalidToken);

        assertTrue(subject.isEmpty(), "Expected empty subject for invalid token");
    }

    @Test
    public void testGenerateToken_Exception() {
        ReflectionTestUtils.setField(tokenService, "secret", null); // Invalidar o segredo

        UserModel user = new UserModel("test@example.com", "Test User", "password", null);

        assertThrows(RuntimeException.class, () -> tokenService.generateToken(user));
    }
}
