package com.api.tags.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.api.tags.follow.FollowController;
import com.api.tags.follow.FollowService;

@ExtendWith(MockitoExtension.class)
public class FollowControllerTest {

    @Mock
    private FollowService followService;

    @InjectMocks
    private FollowController followController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(followController).build();
    }

    @Test
    public void testToggleFollow_Success_Follow() throws Exception {
        String followerId = "user1";
        String followedId = "user2";

        when(followService.toggleFollow(followerId, followedId)).thenReturn(true);

        mockMvc.perform(post("/tags/follow")
                .param("followerId", followerId)
                .param("followedId", followedId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.isFollowing").value(true))
            .andExpect(jsonPath("$.message").value("Agora você está seguindo o usuário."));

        verify(followService, times(1)).toggleFollow(followerId, followedId);
    }

    @Test
    public void testToggleFollow_Success_Unfollow() throws Exception {
        String followerId = "user1";
        String followedId = "user2";

        when(followService.toggleFollow(followerId, followedId)).thenReturn(false);

        mockMvc.perform(post("/tags/follow")
                .param("followerId", followerId)
                .param("followedId", followedId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.isFollowing").value(false))
            .andExpect(jsonPath("$.message").value("Você deixou de seguir o usuário."));

        verify(followService, times(1)).toggleFollow(followerId, followedId);
    }

    @Test
    public void testToggleFollow_UserNotFound() throws Exception {
        String followerId = "user1";
        String followedId = "user2";

        doThrow(new IllegalArgumentException("Usuário não encontrado"))
            .when(followService).toggleFollow(followerId, followedId);

        mockMvc.perform(post("/tags/follow")
                .param("followerId", followerId)
                .param("followedId", followedId))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Usuário não encontrado"));

        verify(followService, times(1)).toggleFollow(followerId, followedId);
    }

    @Test
    public void testToggleFollow_InternalServerError() throws Exception {
        String followerId = "user1";
        String followedId = "user2";

        doThrow(new RuntimeException("Unexpected error"))
            .when(followService).toggleFollow(followerId, followedId);

        mockMvc.perform(post("/tags/follow")
                .param("followerId", followerId)
                .param("followedId", followedId))
            .andExpect(status().isInternalServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Erro ao processar a solicitação"));

        verify(followService, times(1)).toggleFollow(followerId, followedId);
    }
}
