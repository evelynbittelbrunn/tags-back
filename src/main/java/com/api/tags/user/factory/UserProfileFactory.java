package com.api.tags.user.factory;

import java.util.Base64;

import com.api.tags.user.definition.UserModel;
import com.api.tags.user.definition.dto.UserProfileEditDTO;

public class UserProfileFactory {

    public static UserModel updateUserProfile(UserModel user, UserProfileEditDTO userProfileDTO) {
        if (userProfileDTO.getName() != null && !userProfileDTO.getName().isEmpty()) {
            user.setName(userProfileDTO.getName());
        }

        if (userProfileDTO.getBio() != null && !userProfileDTO.getBio().isEmpty()) {
            user.setBio(userProfileDTO.getBio());
        }

        if (userProfileDTO.getProfilePicture() != null && !userProfileDTO.getProfilePicture().isEmpty()) {
            String base64Data = userProfileDTO.getProfilePicture();

            if (base64Data.contains(",")) {
                base64Data = base64Data.split(",")[1];
            }

            byte[] profilePictureBytes = Base64.getDecoder().decode(base64Data);
            user.setProfilePicture(profilePictureBytes);
        }

        return user;
    }
}
