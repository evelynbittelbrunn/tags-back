package com.api.tags.factories;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.tags.comment.repository.CommentRepository;
import com.api.tags.like.repository.LikeRepository;
import com.api.tags.post.definition.PostModel;
import com.api.tags.post.definition.dto.NewPostDTO;
import com.api.tags.post.definition.dto.PostDTO;
import com.api.tags.post.factory.PostDTOFactory;
import com.api.tags.user.definition.UserModel;
import com.api.tags.user.definition.dto.UserPostDTO;
import com.api.tags.user.factory.UserDTOFactory;

@ExtendWith(MockitoExtension.class)
public class PostDTOFactoryTest {

    @Mock
    private UserDTOFactory userFactory;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private PostDTOFactory postDTOFactory;

    private UserModel mockUser;
    private PostModel mockPost;

    @BeforeEach
    public void setup() {
        mockUser = new UserModel();
        mockUser.setId("userId");
        mockUser.setName("John Doe");

        mockPost = new PostModel();
        mockPost.setId("postId");
        mockPost.setContent("This is a test post.");
        mockPost.setCreatedAt(LocalDateTime.now());
        mockPost.setUser(mockUser);
    }

    @Test
    public void testCreatePostDTO_Success() {
        String currentUserId = "currentUserId";
        when(likeRepository.existsByUserIdAndPostId(currentUserId, mockPost.getId())).thenReturn(true);
        when(commentRepository.countByPostId(mockPost.getId())).thenReturn(5);
        when(likeRepository.countByPostId(mockPost.getId())).thenReturn(10);
        when(userFactory.createUserPostDTO(mockUser)).thenReturn(new UserPostDTO(mockUser.getId(), mockUser.getName(), null));

        PostDTO postDTO = postDTOFactory.create(mockPost, currentUserId);

        assertNotNull(postDTO);
        assertEquals(mockPost.getId(), postDTO.getId());
        assertEquals(mockPost.getContent(), postDTO.getContent());
        assertEquals(5L, postDTO.getCommentCount());
        assertEquals(10L, postDTO.getLikeCount());
        assertTrue(postDTO.getIsLiked());
        assertEquals(mockUser.getId(), postDTO.getUser().getId());

        verify(likeRepository, times(1)).existsByUserIdAndPostId(currentUserId, mockPost.getId());
        verify(commentRepository, times(1)).countByPostId(mockPost.getId());
        verify(likeRepository, times(1)).countByPostId(mockPost.getId());
        verify(userFactory, times(1)).createUserPostDTO(mockUser);
    }

    @Test
    public void testCreateNewPostDTO_Success() {
        // Simular o retorno do método createUserPostDTO
        UserPostDTO userPostDTO = new UserPostDTO(mockUser.getId(), mockUser.getName(), null);
        when(userFactory.createUserPostDTO(mockUser)).thenReturn(userPostDTO);

        // Chamar o método a ser testado
        NewPostDTO newPostDTO = postDTOFactory.createNewPostDTO(mockPost);

        // Verificar os resultados
        assertNotNull(newPostDTO);
        assertEquals(mockPost.getId(), newPostDTO.getId());
        assertEquals(mockPost.getContent(), newPostDTO.getContent());
        assertEquals(mockUser.getId(), newPostDTO.getUserId());

        // Verificar se o método foi chamado uma vez
        verify(userFactory, times(1)).createUserPostDTO(mockUser);
    }


    @Test
    public void testCreatePostModel_Success() {
        NewPostDTO newPostDTO = new NewPostDTO();
        newPostDTO.setId("postId");
        newPostDTO.setContent("This is a new test post.");
        newPostDTO.setImageUrl("data:image/png;base64," + Base64.getEncoder().encodeToString("imageData".getBytes()));

        PostModel postModel = postDTOFactory.create(newPostDTO, mockUser);

        assertNotNull(postModel);
        assertEquals(newPostDTO.getId(), postModel.getId());
        assertEquals(newPostDTO.getContent(), postModel.getContent());
        assertNotNull(postModel.getImageData());
        assertArrayEquals("imageData".getBytes(), postModel.getImageData());
        assertEquals(mockUser, postModel.getUser());
    }
}
