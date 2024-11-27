package com.api.tags.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.api.tags.category.definition.CategoryModel;
import com.api.tags.category.repository.CategoryRepository;
import com.api.tags.comment.repository.CommentRepository;
import com.api.tags.like.repository.LikeRepository;
import com.api.tags.post.PostService;
import com.api.tags.post.definition.PostModel;
import com.api.tags.post.definition.dto.NewPostDTO;
import com.api.tags.post.definition.dto.PostDTO;
import com.api.tags.post.factory.PostDTOFactory;
import com.api.tags.post.repository.PostRepository;
import com.api.tags.postCategory.definition.PostCategoryModel;
import com.api.tags.postCategory.repository.PostCategoryRepository;
import com.api.tags.user.definition.UserModel;
import com.api.tags.user.repository.UserRepository;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostDTOFactory postDTOFactory;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PostCategoryRepository postCategoryRepository;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private PostService postService;

    private UserModel user;
    private PostModel post;
    private NewPostDTO newPostDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new UserModel();
        user.setId("user123");

        post = new PostModel();
        post.setId("post123");
        post.setUser(user);
        post.setContent("Post content");

        newPostDTO = new NewPostDTO();
        newPostDTO.setUserId("user123");
        newPostDTO.setContent("New post content");
        newPostDTO.setCategoryIds(List.of("category123"));
    }

    @Test
    void testSavePost() {
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));
        when(postDTOFactory.create(newPostDTO, user)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);

        CategoryModel category = new CategoryModel();
        category.setId("category123");
        when(categoryRepository.findById("category123")).thenReturn(Optional.of(category));

        NewPostDTO savedPostDTO = new NewPostDTO();
        when(postDTOFactory.createNewPostDTO(post)).thenReturn(savedPostDTO);

        NewPostDTO result = postService.save(newPostDTO);

        assertNotNull(result);
        verify(postRepository, times(1)).save(post);
        verify(postCategoryRepository, times(1)).save(any(PostCategoryModel.class));
    }

    @Test
    void testFindAllPosts() {
        Pageable pageable = PageRequest.of(0, 10);
        List<PostModel> postList = List.of(post);
        Page<PostModel> postPage = new PageImpl<>(postList, pageable, 1);
        when(postRepository.findPosts("user123", pageable)).thenReturn(postPage);

        PostDTO postDTO = new PostDTO();
        when(postDTOFactory.create(post, "user123")).thenReturn(postDTO);

        List<PostDTO> result = postService.findAll(1, 10, "user123");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(postRepository, times(1)).findPosts("user123", pageable);
    }

    @Test
    void testFindAllPostsByUser() {
        // Configura os parâmetros de paginação
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));

        // Cria uma lista de posts simulada
        List<PostModel> postList = List.of(post);

        // Cria uma página simulada usando a lista de posts
        Page<PostModel> postPage = new PageImpl<>(postList, pageable, postList.size());

        // Configura o mock do repositório
        when(postRepository.findByUserIdOrderByCreatedAtDesc("user123", pageable)).thenReturn(postPage);

        // Configura o mock do DTO factory
        PostDTO postDTO = new PostDTO();
        when(postDTOFactory.create(post, "currentUserId")).thenReturn(postDTO);

        // Chama o método do serviço
        List<PostDTO> result = postService.findAllByUser(1, 10, "user123", "currentUserId");

        // Validações
        assertNotNull(result, "O resultado não deve ser null");
        assertEquals(1, result.size(), "Deve retornar exatamente 1 post");
        verify(postRepository, times(1)).findByUserIdOrderByCreatedAtDesc("user123", pageable);
        verify(postDTOFactory, times(1)).create(post, "currentUserId");
    }

    @Test
    void testDeletePost() {
        when(postRepository.findById("post123")).thenReturn(Optional.of(post));

        postService.deletePostById("post123");

        verify(postCategoryRepository, times(1)).deleteAllByPost(post);
        verify(likeRepository, times(1)).deleteAllByPost(post);
        verify(commentRepository, times(1)).deleteAllByPost(post);
        verify(postRepository, times(1)).delete(post);
    }
}
