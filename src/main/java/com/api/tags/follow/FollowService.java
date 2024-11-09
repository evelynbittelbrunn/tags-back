package com.api.tags.follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.tags.follow.definitions.FollowId;
import com.api.tags.follow.definitions.FollowModel;
import com.api.tags.follow.repository.FollowRepository;
import com.api.tags.user.definition.UserModel;
import com.api.tags.user.repository.UserRepository;

@Service
public class FollowService {
	@Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    public void toggleFollow(String followerId, String followedId) {
        UserModel follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        UserModel followed = userRepository.findById(followedId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        FollowModel existingFollow = followRepository.findByFollowerAndFollowed(follower, followed);

        if (existingFollow != null) {
            followRepository.delete(existingFollow);
        } else {
        	FollowId followId = new FollowId(follower.getId(), followed.getId());
            FollowModel follow = new FollowModel();
            follow.setId(followId);
            follow.setFollower(follower);
            follow.setFollowed(followed);
            followRepository.save(follow);
        }
    }
}
