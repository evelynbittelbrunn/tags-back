package com.api.tags.auth.definition.dto;

import com.api.tags.user.definition.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResgisterDTO {
	private String email;
	
	private String name;

	private String password;
	
	private RoleEnum role;
}
