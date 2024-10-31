package com.api.tags.post.definition.dto;

import java.time.LocalDateTime;

import com.api.tags.user.definition.UserModel;
import com.api.tags.user.definition.dto.UserPostDTO;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PostDTO {
	
	private String id;

    private UserPostDTO user;

    private String content;

    private String imageUrl;
    
    private LocalDateTime createdAt;
    
    private String imageData;
    
}
