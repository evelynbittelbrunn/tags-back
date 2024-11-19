package com.api.tags.user;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.tags.follow.repository.FollowRepository;
import com.api.tags.user.definition.UserModel;
import com.api.tags.user.definition.dto.UserProfileDTO;
import com.api.tags.user.definition.dto.UserProfileEditDTO;
import com.api.tags.user.factory.UserProfileFactory;
import com.api.tags.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private FollowRepository followRepository;
	
	private UserProfileFactory userProfileFactory;

	public UserProfileDTO getUserNameAndBio(String userId, String currentUserId) {
		UserModel user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

	    String profilePicture = user.getProfilePicture() != null 
	            ? Base64.getEncoder().encodeToString(user.getProfilePicture()) 
	            : null;

	    boolean isFollowing = followRepository.existsByFollowerIdAndFollowedId(currentUserId, userId);
	    
	    long followersCount = followRepository.countByFollowedId(userId);
	    
	    long followingCount = followRepository.countByFollowerId(userId);

	    return new UserProfileDTO(user.getName(), user.getBio(), profilePicture, isFollowing, followersCount, followingCount);
	}

	public UserProfileEditDTO updateUserProfile(String userId, UserProfileEditDTO userProfileDTO) {
	    UserModel user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

	    UserProfileFactory.updateUserProfile(user, userProfileDTO);
	    userRepository.save(user);

	    String profilePicture = user.getProfilePicture() != null 
	            ? Base64.getEncoder().encodeToString(user.getProfilePicture()) 
	            : null;

	    return new UserProfileEditDTO(user.getName(), user.getBio(), profilePicture);
	}
}
