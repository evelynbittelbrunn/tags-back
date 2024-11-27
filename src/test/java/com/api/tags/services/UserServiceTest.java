package com.api.tags.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import com.api.tags.follow.repository.FollowRepository;
import com.api.tags.user.definition.UserModel;
import com.api.tags.user.definition.dto.UserProfileDTO;
import com.api.tags.user.definition.dto.UserProfileEditDTO;
import com.api.tags.user.definition.dto.UserSearchDTO;
import com.api.tags.user.factory.UserProfileFactory;
import com.api.tags.user.repository.UserRepository;
import com.api.tags.user.UserService;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private UserService userService;

    private UserModel user;
    private UserProfileEditDTO userProfileEditDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new UserModel();
        user.setId("user123");
        user.setName("John Doe");
        user.setBio("Bio Test");
        user.setProfilePicture("profile-picture".getBytes());

        userProfileEditDTO = new UserProfileEditDTO("Updated Name", "Updated Bio", null);
    }

    @Test
    void testGetUserNameAndBio() {
        // Mocka o retorno do usuário
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));
        // Mocka os métodos do FollowRepository
        when(followRepository.existsByFollowerIdAndFollowedId("currentUser123", "user123")).thenReturn(true);
        when(followRepository.countByFollowedId("user123")).thenReturn(10L);
        when(followRepository.countByFollowerId("user123")).thenReturn(5L);

        // Chama o método do serviço
        UserProfileDTO result = userService.getUserNameAndBio("user123", "currentUser123");

        // Verifica se os valores estão corretos
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("Bio Test", result.getBio());
        assertTrue(result.isFollowing());
        assertEquals(10L, result.getFollowersCount());
        assertEquals(5L, result.getFollowingCount());
        assertEquals(Base64.getEncoder().encodeToString(user.getProfilePicture()), result.getProfilePicture());

        // Verifica interações com o repositório
        verify(userRepository, times(1)).findById("user123");
        verify(followRepository, times(1)).existsByFollowerIdAndFollowedId("currentUser123", "user123");
        verify(followRepository, times(1)).countByFollowedId("user123");
        verify(followRepository, times(1)).countByFollowerId("user123");
    }

    @Test
    void testUpdateUserProfile() {
        // Mocka o retorno do usuário
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        // Mocka a atualização de perfil no factory
        UserProfileFactory.updateUserProfile(user, userProfileEditDTO);

        // Chama o método do serviço
        UserProfileEditDTO result = userService.updateUserProfile("user123", userProfileEditDTO);

        // Verifica os valores retornados
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals("Updated Bio", result.getBio());
        assertEquals(Base64.getEncoder().encodeToString(user.getProfilePicture()), result.getProfilePicture());

        // Verifica interações com o repositório
        verify(userRepository, times(1)).findById("user123");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testSearchUsers() {
        // Configura os parâmetros de paginação
        Pageable pageable = PageRequest.of(0, 10);
        List<UserModel> userList = List.of(user);
        Page<UserModel> userPage = new PageImpl<>(userList, pageable, userList.size());

        // Mocka o comportamento do repositório
        when(userRepository.findByNameContainingIgnoreCase("John", pageable)).thenReturn(userPage);

        // Chama o método do serviço
        List<UserSearchDTO> result = userService.searchUsers("John", pageable);

        // Verifica os valores retornados
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("user123", result.get(0).getId());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Bio Test", result.get(0).getBio());
        assertEquals(Base64.getEncoder().encodeToString(user.getProfilePicture()), result.get(0).getProfilePicture());

        // Verifica interações com o repositório
        verify(userRepository, times(1)).findByNameContainingIgnoreCase("John", pageable);
    }
}
