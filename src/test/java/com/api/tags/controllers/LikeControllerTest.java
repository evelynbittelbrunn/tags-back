package com.api.tags.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.api.tags.like.LikeController;
import com.api.tags.like.LikeService;

@ExtendWith(MockitoExtension.class)
public class LikeControllerTest {

    @Mock
    private LikeService likeService;

    @InjectMocks
    private LikeController likeController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(likeController).build();
    }

    @Test
    public void testToggleLike_Success() throws Exception {
        String userId = "user123";
        String postId = "post123";

        doNothing().when(likeService).toggleLike(userId, postId);

        mockMvc.perform(post("/tags/like")
                .param("userId", userId)
                .param("postId", postId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Ação de curtida realizada com sucesso"));

        verify(likeService, times(1)).toggleLike(userId, postId);
    }

    @Test
    public void testToggleLike_BadRequest() throws Exception {
        String userId = "user123";
        String postId = "post123";

        doThrow(new IllegalArgumentException("Usuário ou post não encontrado"))
            .when(likeService).toggleLike(userId, postId);

        mockMvc.perform(post("/tags/like")
                .param("userId", userId)
                .param("postId", postId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Usuário ou post não encontrado"));

        verify(likeService, times(1)).toggleLike(userId, postId);
    }
}
