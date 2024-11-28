package com.api.tags.userCategory.definition.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCategoryRequestDTO {
	
	private String userId;
	
    private List<String> categoryIds;
    
}
