package com.api.tags.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.tags.follow.definitions.FollowId;
import com.api.tags.follow.definitions.FollowModel;
import com.api.tags.user.definition.UserModel;

public interface FollowRepository extends JpaRepository<FollowModel, FollowId> {
	FollowModel findByFollowerAndFollowed(UserModel follower, UserModel followed);
	
	boolean existsByFollowerIdAndFollowedId(String followerId, String followedId);
}
