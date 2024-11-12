package com.api.tags.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.tags.like.definition.LikeModel;
import com.api.tags.post.definition.PostModel;
import com.api.tags.user.definition.UserModel;

public interface LikeRepository extends JpaRepository<LikeModel, String> {
	boolean existsByUserAndPost(UserModel user, PostModel post);
	
	boolean existsByUserIdAndPostId(String userId, String postId);
	
	void deleteByUserAndPost(UserModel user, PostModel post);
	
	void deleteAllByPost(PostModel post);
}
