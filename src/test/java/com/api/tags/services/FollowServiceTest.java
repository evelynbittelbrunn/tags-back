package com.api.tags.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.api.tags.follow.FollowService;
import com.api.tags.follow.definitions.FollowId;
import com.api.tags.follow.definitions.FollowModel;
import com.api.tags.follow.repository.FollowRepository;
import com.api.tags.user.definition.UserModel;
import com.api.tags.user.repository.UserRepository;

class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FollowService followService;

    private UserModel follower;
    private UserModel followed;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        follower = new UserModel();
        follower.setId("follower123");

        followed = new UserModel();
        followed.setId("followed123");
    }

    @Test
    void testToggleFollow_NewFollow() {
        // Mock de busca de usuários
        when(userRepository.findById("follower123")).thenReturn(Optional.of(follower));
        when(userRepository.findById("followed123")).thenReturn(Optional.of(followed));

        // Mock de inexistência de follow
        when(followRepository.findByFollowerAndFollowed(follower, followed)).thenReturn(null);

        // Chamar o método
        boolean result = followService.toggleFollow("follower123", "followed123");

        // Verificar comportamento
        assertTrue(result, "Deve retornar true para novo follow");
        verify(followRepository, times(1)).save(any(FollowModel.class));
    }

    @Test
    void testToggleFollow_RemoveFollow() {
        // Mock de busca de usuários
        when(userRepository.findById("follower123")).thenReturn(Optional.of(follower));
        when(userRepository.findById("followed123")).thenReturn(Optional.of(followed));

        // Mock de existência de follow
        FollowModel existingFollow = new FollowModel();
        existingFollow.setId(new FollowId("follower123", "followed123"));
        existingFollow.setFollower(follower);
        existingFollow.setFollowed(followed);

        when(followRepository.findByFollowerAndFollowed(follower, followed)).thenReturn(existingFollow);

        // Chamar o método
        boolean result = followService.toggleFollow("follower123", "followed123");

        // Verificar comportamento
        assertFalse(result, "Deve retornar false ao remover follow");
        verify(followRepository, times(1)).delete(existingFollow);
    }

    @Test
    void testToggleFollow_UserNotFound() {
        // Mock de busca de usuário não encontrado
        when(userRepository.findById("follower123")).thenReturn(Optional.empty());

        // Chamar o método e esperar exceção
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            followService.toggleFollow("follower123", "followed123")
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(followRepository, never()).save(any(FollowModel.class));
        verify(followRepository, never()).delete(any(FollowModel.class));
    }
}
