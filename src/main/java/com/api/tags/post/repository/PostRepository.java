package com.api.tags.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.tags.post.definition.PostModel;

@Repository
public interface PostRepository extends JpaRepository<PostModel, String> {
	

	Page<PostModel> findAll(Pageable pageable);
	
	@Query("""
	    SELECT DISTINCT p
	    FROM PostModel p
	    LEFT JOIN PostCategoryModel pc ON pc.post.id = p.id
	    LEFT JOIN UserCategoryModel uc ON uc.category.id = pc.category.id
	    LEFT JOIN FollowModel f ON f.followed.id = p.user.id
	    WHERE (uc.user.id = :userId OR f.follower.id = :userId OR p.user.id = :userId)
	    ORDER BY p.createdAt DESC
	""")
	Page<PostModel> findPosts(@Param("userId") String userId, Pageable pageable);
	
	Page<PostModel> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);
}
