package com.api.tags.user.factory;

import java.util.Base64;

import org.springframework.stereotype.Component;

import com.api.tags.user.definition.UserModel;
import com.api.tags.user.definition.dto.UserPostDTO;

@Component
public class UserDTOFactory {
    public UserPostDTO createUserPostDTO(UserModel userModel) {
        
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setId(userModel.getId().toString());
        userPostDTO.setName(userModel.getName());
        
        if (userModel.getProfilePicture() != null) {
            String base64ProfilePicture = Base64.getEncoder().encodeToString(userModel.getProfilePicture());
            userPostDTO.setProfilePicture(base64ProfilePicture);
        } else {
            userPostDTO.setProfilePicture(null);
        }

        return userPostDTO;
    }
}
