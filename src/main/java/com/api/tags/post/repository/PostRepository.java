package com.api.tags.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.tags.post.definition.PostModel;

@Repository
public interface PostRepository extends JpaRepository<PostModel, String> {
	Page<PostModel> findAll(Pageable pageable);
	
	Page<PostModel> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);
}
