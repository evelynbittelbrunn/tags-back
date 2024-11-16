package com.api.tags.comment.definition.dto;

import com.api.tags.category.definition.dto.CategoryDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
	
    private String userId;
	
    private String postId;
    
    private String content;
    
}
