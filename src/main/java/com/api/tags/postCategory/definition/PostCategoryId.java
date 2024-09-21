package com.api.tags.postCategory.definition;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class PostCategoryId {
	private String postId;
    private String categoryId;
}
