package com.api.tags.comment;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.tags.comment.definition.CommentModel;
import com.api.tags.comment.definition.dto.CommentDTO;
import com.api.tags.comment.definition.dto.NewCommentDTO;
import com.api.tags.comment.repository.CommentRepository;
import com.api.tags.post.repository.PostRepository;
import com.api.tags.user.definition.dto.UserPostDTO;
import com.api.tags.user.factory.UserDTOFactory;
import com.api.tags.user.repository.UserRepository;

@Service
public class CommentService {
	
	@Autowired
    private UserDTOFactory userFactory;
	
	@Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;
	
    public CommentDTO createComment(NewCommentDTO commentDTO) {

        CommentModel commentModel = new CommentModel();
        commentModel.setContent(commentDTO.getContent());
        commentModel.setUser(userRepository.findById(commentDTO.getUserId())
                                           .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado")));
        commentModel.setPost(postRepository.findById(commentDTO.getPostId())
                                           .orElseThrow(() -> new IllegalArgumentException("Post não encontrado")));
        commentModel = commentRepository.save(commentModel);
        
        String profilePictureBase64 = null;
        if (commentModel.getUser().getProfilePicture() != null) {
            profilePictureBase64 = Base64.getEncoder().encodeToString(commentModel.getUser().getProfilePicture());
        }
        
        CommentDTO responseDTO = new CommentDTO();
        responseDTO.setId(commentModel.getId());
        responseDTO.setContent(commentModel.getContent());
        responseDTO.setUser(new UserPostDTO(
            commentModel.getUser().getId(),
            commentModel.getUser().getName(),
            profilePictureBase64
        ));
        
        return responseDTO;
    }
    
    public List<CommentDTO> getCommentsByPostId(String postId) {
        List<CommentModel> comments = commentRepository.findByPostIdOrderByCreatedAtDesc(postId);

        return comments.stream().map(comment -> {
            UserPostDTO userPostDTO = userFactory.createUserPostDTO(comment.getUser());
            return new CommentDTO(comment.getId(), userPostDTO, comment.getContent());
        }).collect(Collectors.toList());
    }
}
