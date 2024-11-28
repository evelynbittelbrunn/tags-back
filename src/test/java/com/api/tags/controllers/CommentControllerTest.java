package com.api.tags.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.api.tags.comment.CommentController;
import com.api.tags.comment.CommentService;
import com.api.tags.comment.definition.dto.CommentDTO;
import com.api.tags.comment.definition.dto.NewCommentDTO;
import com.api.tags.user.definition.dto.UserPostDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateComment_Success() throws Exception {
        // Simulação dos dados de entrada e saída
        NewCommentDTO newCommentDTO = new NewCommentDTO("userId", "postId", "This is a comment");
        UserPostDTO userPostDTO = new UserPostDTO("userId", "User Name", "profilePic.jpg");
        CommentDTO createdCommentDTO = new CommentDTO("commentId", userPostDTO, "This is a comment");

        // Configuração do mock para simular o comportamento do serviço
        when(commentService.createComment(any(NewCommentDTO.class))).thenReturn(createdCommentDTO);

        // Executa o teste
        mockMvc.perform(post("/tags/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCommentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("commentId"))
                .andExpect(jsonPath("$.content").value("This is a comment"))
                .andExpect(jsonPath("$.user.id").value("userId")) // Acessando o campo aninhado
                .andExpect(jsonPath("$.user.name").value("User Name"))
                .andExpect(jsonPath("$.user.profilePicture").value("profilePic.jpg"));

        // Verificação da interação com o serviço
        verify(commentService, times(1)).createComment(any(NewCommentDTO.class));
    }

    @Test
    public void testGetCommentsByPostId_Success() throws Exception {
        String postId = "postId";
        UserPostDTO userPostDTO1 = new UserPostDTO("userId1", "User One", "profilePic1.jpg");
        UserPostDTO userPostDTO2 = new UserPostDTO("userId2", "User Two", "profilePic2.jpg");

        List<CommentDTO> comments = Arrays.asList(
                new CommentDTO("commentId1", userPostDTO1, "First comment"),
                new CommentDTO("commentId2", userPostDTO2, "Second comment")
        );

        when(commentService.getCommentsByPostId(postId)).thenReturn(comments);

        mockMvc.perform(get("/tags/comments/post/{postId}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("commentId1"))
                .andExpect(jsonPath("$[0].content").value("First comment"))
                .andExpect(jsonPath("$[0].user.id").value("userId1"))
                .andExpect(jsonPath("$[0].user.name").value("User One"))
                .andExpect(jsonPath("$[1].id").value("commentId2"))
                .andExpect(jsonPath("$[1].content").value("Second comment"))
                .andExpect(jsonPath("$[1].user.id").value("userId2"))
                .andExpect(jsonPath("$[1].user.name").value("User Two"));

        verify(commentService, times(1)).getCommentsByPostId(postId);
    }

    @Test
    public void testDeleteComment_Success() throws Exception {
        String commentId = "commentId";

        doNothing().when(commentService).deleteComment(commentId);

        mockMvc.perform(delete("/tags/comments/{commentId}", commentId))
                .andExpect(status().isOk())
                .andExpect(content().string("Comentário excluído com sucesso"));

        verify(commentService, times(1)).deleteComment(commentId);
    }

    @Test
    public void testDeleteComment_NotFound() throws Exception {
        String commentId = "commentId";

        doThrow(new IllegalArgumentException("Comentário não encontrado"))
                .when(commentService).deleteComment(commentId);

        mockMvc.perform(delete("/tags/comments/{commentId}", commentId))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Comentário não encontrado"));

        verify(commentService, times(1)).deleteComment(commentId);
    }
}
