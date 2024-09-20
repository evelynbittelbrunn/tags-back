package com.api.tags.auth.definition.dto;

import com.api.tags.user.definition.RoleEnum;

import lombok.Data;

@Data
public class ResgisterDTO {
	private String email;

	private String password;
	
	private RoleEnum role;
}
