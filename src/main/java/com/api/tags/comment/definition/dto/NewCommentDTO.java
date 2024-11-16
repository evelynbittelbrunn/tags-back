package com.api.tags.comment.definition.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCommentDTO {
	
    private String userId;
	
    private String postId;
    
    private String content;
    
}
