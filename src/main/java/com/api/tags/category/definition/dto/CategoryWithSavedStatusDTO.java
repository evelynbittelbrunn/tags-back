package com.api.tags.category.definition.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryWithSavedStatusDTO {
	
    private String id;
    
    private String description;
    
    private boolean savedByUser;
    
}
