package com.api.tags.user;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.tags.user.definition.UserModel;
import com.api.tags.user.definition.dto.UserProfileDTO;
import com.api.tags.user.factory.UserProfileFactory;
import com.api.tags.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;
	
	private UserProfileFactory userProfileFactory;

	public UserProfileDTO getUserNameAndBio(String userId) {
	    UserModel user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

	    String profilePicture = user.getProfilePicture() != null 
	            ? Base64.getEncoder().encodeToString(user.getProfilePicture()) 
	            : null;

	    return new UserProfileDTO(user.getName(), user.getBio(), profilePicture);
	}

	public UserProfileDTO updateUserProfile(String userId, UserProfileDTO userProfileDTO) {
	    UserModel user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

	    UserProfileFactory.updateUserProfile(user, userProfileDTO);
	    userRepository.save(user);

	    String profilePicture = user.getProfilePicture() != null 
	            ? Base64.getEncoder().encodeToString(user.getProfilePicture()) 
	            : null;

	    return new UserProfileDTO(user.getName(), user.getBio(), profilePicture);
	}
}
