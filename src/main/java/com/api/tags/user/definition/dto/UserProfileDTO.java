package com.api.tags.user.definition.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileDTO {
	
	private String name;
	
	private String bio;

	private String profilePicture;
}
