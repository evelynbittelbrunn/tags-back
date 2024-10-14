package com.api.tags.user.definition.dto;

import java.time.LocalDateTime;

import com.api.tags.user.definition.RoleEnum;

import lombok.Data;

@Data
public class UserDTO {
	
	private String id;
	
	private String name;
	
	private String profilePicture;
}
