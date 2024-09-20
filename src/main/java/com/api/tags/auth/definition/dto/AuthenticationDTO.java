package com.api.tags.auth.definition.dto;

import org.antlr.v4.runtime.misc.NotNull;

import lombok.Data;

@Data
public class AuthenticationDTO {

	private String email;

	private String password;
}
