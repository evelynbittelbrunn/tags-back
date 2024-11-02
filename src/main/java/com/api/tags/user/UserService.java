package com.api.tags.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.tags.user.definition.UserModel;
import com.api.tags.user.definition.dto.UserDTO;
import com.api.tags.user.definition.dto.UserProfileDTO;
import com.api.tags.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;

    public UserProfileDTO getUserNameAndBio(String userId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UserProfileDTO(user.getName(), user.getBio());
    }
}
