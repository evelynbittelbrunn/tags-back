package com.api.tags.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.api.tags.like.LikeService;
import com.api.tags.like.definition.LikeModel;
import com.api.tags.like.repository.LikeRepository;
import com.api.tags.post.definition.PostModel;
import com.api.tags.post.repository.PostRepository;
import com.api.tags.user.definition.UserModel;
import com.api.tags.user.repository.UserRepository;

class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private LikeService likeService;

    private UserModel user;
    private PostModel post;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new UserModel();
        user.setId("user123");

        post = new PostModel();
        post.setId("post123");
    }

    @Test
    void testToggleLike_AddLike() {
        // Mock de busca de usuário e post
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));
        when(postRepository.findById("post123")).thenReturn(Optional.of(post));

        // Mock de inexistência de like
        when(likeRepository.existsByUserAndPost(user, post)).thenReturn(false);

        // Chamar o método
        likeService.toggleLike("user123", "post123");

        // Verificar comportamento
        verify(likeRepository, times(1)).save(any(LikeModel.class));
        verify(likeRepository, never()).deleteByUserAndPost(user, post);
    }

    @Test
    void testToggleLike_RemoveLike() {
        // Mock de busca de usuário e post
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));
        when(postRepository.findById("post123")).thenReturn(Optional.of(post));

        // Mock de existência de like
        when(likeRepository.existsByUserAndPost(user, post)).thenReturn(true);

        // Chamar o método
        likeService.toggleLike("user123", "post123");

        // Verificar comportamento
        verify(likeRepository, times(1)).deleteByUserAndPost(user, post);
        verify(likeRepository, never()).save(any(LikeModel.class));
    }

    @Test
    void testToggleLike_UserNotFound() {
        // Mock de busca de usuário não encontrado
        when(userRepository.findById("user123")).thenReturn(Optional.empty());

        // Chamar o método e verificar exceção
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            likeService.toggleLike("user123", "post123")
        );

        assertEquals("Usuário não encontrado.", exception.getMessage());
        verify(likeRepository, never()).save(any(LikeModel.class));
        verify(likeRepository, never()).deleteByUserAndPost(any(UserModel.class), any(PostModel.class));
    }

    @Test
    void testToggleLike_PostNotFound() {
        // Mock de busca de usuário existente
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));
        
        // Mock de busca de post não encontrado
        when(postRepository.findById("post123")).thenReturn(Optional.empty());

        // Chamar o método e verificar exceção
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            likeService.toggleLike("user123", "post123")
        );

        assertEquals("Postagem não encontrada.", exception.getMessage());
        verify(likeRepository, never()).save(any(LikeModel.class));
        verify(likeRepository, never()).deleteByUserAndPost(any(UserModel.class), any(PostModel.class));
    }
}
