package com.api.tags.userCategory.definition.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCategoryRequestDTO {
	
	private String userId;
	
    private List<String> categoryIds;
    
}
