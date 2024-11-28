package com.api.tags.user.definition.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileDTO {
	
	private String name;
	
	private String bio;

	private String profilePicture;
	
	@JsonProperty("isFollowing")
	private boolean isFollowing;
	
	private long followersCount;
	
	private long followingCount;
}
