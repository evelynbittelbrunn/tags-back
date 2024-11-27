package com.api.tags.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import com.api.tags.comment.CommentService;
import com.api.tags.comment.definition.CommentModel;
import com.api.tags.comment.definition.dto.CommentDTO;
import com.api.tags.comment.definition.dto.NewCommentDTO;
import com.api.tags.user.definition.UserModel;
import com.api.tags.post.definition.PostModel;
import com.api.tags.user.repository.UserRepository;
import com.api.tags.post.repository.PostRepository;
import com.api.tags.comment.repository.CommentRepository;

class CommentServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private UserModel user;
    private PostModel post;
    private NewCommentDTO newCommentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        user = new UserModel();
        user.setId("user123");
        user.setName("John Doe");
        
        post = new PostModel();
        post.setId("post123");

        newCommentDTO = new NewCommentDTO();
        newCommentDTO.setUserId("user123");
        newCommentDTO.setPostId("post123");
        newCommentDTO.setContent("This is a test comment.");
    }

    @Test
    void testCreateComment() {
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));
        when(postRepository.findById("post123")).thenReturn(Optional.of(post));
        when(commentRepository.save(any(CommentModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CommentDTO result = commentService.createComment(newCommentDTO);

        assertNotNull(result);
        assertEquals(newCommentDTO.getContent(), result.getContent());
        assertEquals("user123", result.getUser().getId());
        assertEquals("John Doe", result.getUser().getName());

        String profilePictureBase64 = result.getUser().getProfilePicture();
        if (user.getProfilePicture() != null) {
            assertEquals(Base64.getEncoder().encodeToString(user.getProfilePicture()), profilePictureBase64);
        } else {
            assertNull(profilePictureBase64);
        }

        verify(userRepository, times(1)).findById("user123");
        verify(postRepository, times(1)).findById("post123");
        verify(commentRepository, times(1)).save(any(CommentModel.class));
    }
}
