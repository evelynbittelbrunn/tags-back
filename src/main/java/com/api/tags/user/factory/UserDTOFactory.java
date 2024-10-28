package com.api.tags.user.factory;

import org.springframework.stereotype.Component;

import com.api.tags.user.definition.UserModel;
import com.api.tags.user.definition.dto.UserDTO;
import com.api.tags.user.definition.dto.UserPostDTO;

@Component
public class UserDTOFactory {
	public UserPostDTO createUserPostDTO(UserModel userModel) {
		
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setId(userModel.getId().toString());
        userPostDTO.setName(userModel.getName());
        userPostDTO.setProfilePicture(userModel.getProfilePicture());
        
        return userPostDTO;
    }
}
