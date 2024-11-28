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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.api.tags.post.PostController;
import com.api.tags.post.PostService;
import com.api.tags.post.definition.dto.NewPostDTO;
import com.api.tags.post.definition.dto.PostDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSavePost_Success() throws Exception {
        NewPostDTO postDTO = new NewPostDTO();
        postDTO.setUserId("userId");
        postDTO.setContent("Content of the post");
        postDTO.setCategoryIds(Arrays.asList("category1", "category2"));

        NewPostDTO savedPostDTO = new NewPostDTO();
        savedPostDTO.setId("postId");
        savedPostDTO.setUserId("userId");
        savedPostDTO.setContent("Content of the post");
        savedPostDTO.setCategoryIds(Arrays.asList("category1", "category2"));

        when(postService.save(any(NewPostDTO.class))).thenReturn(savedPostDTO);

        mockMvc.perform(post("/tags/posts/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value("postId"))
            .andExpect(jsonPath("$.userId").value("userId"))
            .andExpect(jsonPath("$.content").value("Content of the post"));

        verify(postService, times(1)).save(any(NewPostDTO.class));
    }

    @Test
    public void testFindAllPosts_Success() throws Exception {
        int pagination = 1;
        int items = 10;
        String currentUserId = "currentUserId";

        PostDTO postDTO = new PostDTO();
        postDTO.setId("postId");
        postDTO.setContent("Content");

        List<PostDTO> postList = Arrays.asList(postDTO);

        when(postService.findAll(pagination, items, currentUserId)).thenReturn(postList);

        mockMvc.perform(get("/tags/posts/find-all")
                .param("pagination", String.valueOf(pagination))
                .param("items", String.valueOf(items))
                .param("currentUserId", currentUserId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value("postId"))
            .andExpect(jsonPath("$[0].content").value("Content"));

        verify(postService, times(1)).findAll(pagination, items, currentUserId);
    }

    @Test
    public void testFindAllByUser_Success() throws Exception {
        int pagination = 1;
        int items = 10;
        String userId = "userId";
        String currentUserId = "currentUserId";

        PostDTO postDTO = new PostDTO();
        postDTO.setId("postId");
        postDTO.setContent("Content");

        List<PostDTO> postList = Arrays.asList(postDTO);

        when(postService.findAllByUser(pagination, items, userId, currentUserId)).thenReturn(postList);

        mockMvc.perform(get("/tags/posts/{userId}", userId)
                .param("pagination", String.valueOf(pagination))
                .param("items", String.valueOf(items))
                .param("currentUserId", currentUserId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value("postId"))
            .andExpect(jsonPath("$[0].content").value("Content"));

        verify(postService, times(1)).findAllByUser(pagination, items, userId, currentUserId);
    }

    @Test
    public void testDeletePost_Success() throws Exception {
        String postId = "postId";

        doNothing().when(postService).deletePostById(postId);

        mockMvc.perform(delete("/tags/posts/delete-post/{postId}", postId))
            .andExpect(status().isOk())
            .andExpect(content().string("Postagem excluída com sucesso"));

        verify(postService, times(1)).deletePostById(postId);
    }

    @Test
    public void testDeletePost_NotFound() throws Exception {
        String postId = "postId";

        doThrow(new RuntimeException("Postagem não encontrada"))
            .when(postService).deletePostById(postId);

        mockMvc.perform(delete("/tags/posts/delete-post/{postId}", postId))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Postagem não encontrada"));

        verify(postService, times(1)).deletePostById(postId);
    }
}
