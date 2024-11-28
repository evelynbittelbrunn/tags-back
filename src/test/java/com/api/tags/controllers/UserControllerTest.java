package com.api.tags.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.api.tags.user.definition.dto.UserProfileDTO;
import com.api.tags.user.definition.dto.UserProfileEditDTO;
import com.api.tags.user.definition.dto.UserSearchDTO;
import com.api.tags.user.UserController;
import com.api.tags.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetUserNameAndBio_Success() throws Exception {
        String userId = "user123";
        String currentUserId = "currentUser456";

        UserProfileDTO userProfileDTO = new UserProfileDTO(
            "John Doe",
            "Bio of John",
            "profilePicUrl",
            true,
            100L,
            50L
        );

        when(userService.getUserNameAndBio(userId, currentUserId)).thenReturn(userProfileDTO);

        mockMvc.perform(get("/tags/users/profile/{userId}", userId)
                .param("currentUserId", currentUserId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("John Doe"))
            .andExpect(jsonPath("$.bio").value("Bio of John"))
            .andExpect(jsonPath("$.profilePicture").value("profilePicUrl"))
            .andExpect(jsonPath("$.isFollowing").value(true))
            .andExpect(jsonPath("$.followersCount").value(100))
            .andExpect(jsonPath("$.followingCount").value(50));

        verify(userService, times(1)).getUserNameAndBio(userId, currentUserId);
    }

    @Test
    public void testUpdateUserProfile_Success() throws Exception {
        String userId = "user123";

        UserProfileEditDTO userProfileEditDTO = new UserProfileEditDTO(
            "John Doe",
            "Updated bio",
            "updatedProfilePicUrl"
        );

        when(userService.updateUserProfile(eq(userId), any(UserProfileEditDTO.class)))
            .thenReturn(userProfileEditDTO);

        mockMvc.perform(put("/tags/users/profile/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProfileEditDTO)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("John Doe"))
            .andExpect(jsonPath("$.bio").value("Updated bio"))
            .andExpect(jsonPath("$.profilePicture").value("updatedProfilePicUrl"));

        verify(userService, times(1)).updateUserProfile(eq(userId), any(UserProfileEditDTO.class));
    }

    @Test
    public void testSearchUsers_Success() throws Exception {
        int pagination = 1;
        int items = 10;
        String name = "John";

        List<UserSearchDTO> users = Arrays.asList(
            new UserSearchDTO("user1", "John Doe", "Bio of John", "profilePicUrl1"),
            new UserSearchDTO("user2", "Johnny Depp", "Bio of Johnny", "profilePicUrl2")
        );

        when(userService.searchUsers(eq(name), any())).thenReturn(users);

        mockMvc.perform(get("/tags/users/search")
                .param("pagination", String.valueOf(pagination))
                .param("items", String.valueOf(items))
                .param("name", name))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value("user1"))
            .andExpect(jsonPath("$[0].name").value("John Doe"))
            .andExpect(jsonPath("$[0].bio").value("Bio of John"))
            .andExpect(jsonPath("$[0].profilePicture").value("profilePicUrl1"))
            .andExpect(jsonPath("$[1].id").value("user2"))
            .andExpect(jsonPath("$[1].name").value("Johnny Depp"))
            .andExpect(jsonPath("$[1].bio").value("Bio of Johnny"))
            .andExpect(jsonPath("$[1].profilePicture").value("profilePicUrl2"));

        verify(userService, times(1)).searchUsers(eq(name), any());
    }
}
