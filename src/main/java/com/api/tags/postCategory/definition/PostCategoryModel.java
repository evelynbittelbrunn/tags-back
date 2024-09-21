package com.api.tags.postCategory.definition;

import com.api.tags.category.definition.CategoryModel;
import com.api.tags.post.definition.PostModel;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "post_categories")
public class PostCategoryModel {

	@EmbeddedId
	private PostCategoryId id;
	
	@ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private PostModel post;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private CategoryModel category;
}
