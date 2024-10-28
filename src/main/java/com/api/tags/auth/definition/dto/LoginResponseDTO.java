package com.api.tags.auth.definition.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
	
	private String token;
	
	private String userId;
}
