package com.api.tags.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.tags.comment.definition.CommentModel;
import com.api.tags.comment.definition.dto.CommentDTO;
import com.api.tags.comment.repository.CommentRepository;
import com.api.tags.post.definition.PostModel;
import com.api.tags.post.repository.PostRepository;
import com.api.tags.user.definition.UserModel;
import com.api.tags.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommentService {
	
	@Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;
	
    public CommentModel createComment(CommentDTO commentDTO) {
        UserModel user = userRepository.findById(commentDTO.getUserId())
        		.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + commentDTO.getUserId()));

        PostModel post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post não encontrado com ID: " + commentDTO.getPostId()));

        CommentModel comment = new CommentModel();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(commentDTO.getContent());
        return commentRepository.save(comment);
    }
}
