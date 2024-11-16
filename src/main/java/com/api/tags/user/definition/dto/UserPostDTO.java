package com.api.tags.user.definition.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostDTO {
	private String id;
	
	private String name;
	
	private String profilePicture;
}