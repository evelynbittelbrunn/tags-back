package com.api.tags.post.definition.dto;

import java.time.LocalDateTime;

import com.api.tags.user.definition.dto.UserPostDTO;

import lombok.Data;

@Data
public class NewPostDTO {

	private String id;

    private String userId;

    private String content;

    private String imageUrl;
    
    private LocalDateTime createdAt;
    
}
