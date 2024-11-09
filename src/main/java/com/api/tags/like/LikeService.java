package com.api.tags.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.tags.like.definition.LikeModel;
import com.api.tags.like.repository.LikeRepository;
import com.api.tags.post.definition.PostModel;
import com.api.tags.post.repository.PostRepository;
import com.api.tags.user.definition.UserModel;
import com.api.tags.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class LikeService {
	@Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public void toggleLike(String userId, String postId) {

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        PostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Postagem não encontrada."));

        if (likeRepository.existsByUserAndPost(user, post)) {
            likeRepository.deleteByUserAndPost(user, post);
        } else {
            LikeModel like = new LikeModel();
            like.setUser(user);
            like.setPost(post);
            likeRepository.save(like);
        }
    }
}
