package com.api.tags.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.tags.comment.definition.CommentModel;
import com.api.tags.post.definition.PostModel;

public interface CommentRepository extends JpaRepository<CommentModel, String> {
	void deleteAllByPost(PostModel post);
	
	List<CommentModel> findByPostIdOrderByCreatedAtDesc(String postId);
}
