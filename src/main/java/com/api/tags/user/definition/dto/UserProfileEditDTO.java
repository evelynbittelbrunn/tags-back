package com.api.tags.user.definition.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileEditDTO {
	private String name;
	
	private String bio;

	private String profilePicture;
}
