package com.api.tags.comment.definition.dto;

import com.api.tags.user.definition.dto.UserPostDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
	
    private String id;

    private UserPostDTO user;

    private String content;
    
}
