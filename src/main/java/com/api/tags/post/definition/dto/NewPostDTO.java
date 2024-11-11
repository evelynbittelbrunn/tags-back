package com.api.tags.post.definition.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class NewPostDTO {

	private String id;

    private String userId;

    private String content;

    private String imageUrl;
    
    private LocalDateTime createdAt;
    
    private List<String> categoryIds;
    
}
