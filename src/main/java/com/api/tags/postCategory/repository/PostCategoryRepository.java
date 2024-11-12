package com.api.tags.postCategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.tags.post.definition.PostModel;
import com.api.tags.postCategory.definition.PostCategoryId;
import com.api.tags.postCategory.definition.PostCategoryModel;

@Repository
public interface PostCategoryRepository extends JpaRepository<PostCategoryModel, PostCategoryId> {
	void deleteAllByPost(PostModel post);
}
