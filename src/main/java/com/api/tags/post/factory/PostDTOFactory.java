package com.api.tags.post.factory;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.tags.comment.repository.CommentRepository;
import com.api.tags.like.repository.LikeRepository;
import com.api.tags.post.definition.PostModel;
import com.api.tags.post.definition.dto.NewPostDTO;
import com.api.tags.post.definition.dto.PostDTO;
import com.api.tags.user.definition.UserModel;
import com.api.tags.user.definition.dto.UserPostDTO;
import com.api.tags.user.factory.UserDTOFactory;

@Component
public class PostDTOFactory {
	
	@Autowired
    private UserDTOFactory userFactory;
	
	@Autowired
    private LikeRepository likeRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	public PostDTO create(PostModel postModel, String currentUserId) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postModel.getId());
        postDTO.setContent(postModel.getContent());
        
        if (postModel.getImageData() != null) {
            String base64Image = Base64.getEncoder().encodeToString(postModel.getImageData());
            postDTO.setImageData(base64Image);
        }

        boolean isLiked = likeRepository.existsByUserIdAndPostId(currentUserId, postModel.getId());
        postDTO.setIsLiked(isLiked);

        UserPostDTO userPostDTO = userFactory.createUserPostDTO(postModel.getUser());
        postDTO.setUser(userPostDTO);
        
        postDTO.setCommentCount(commentRepository.countByPostId(postModel.getId()));
        postDTO.setLikeCount(likeRepository.countByPostId(postModel.getId()));
        postDTO.setCreatedAt(postModel.getCreatedAt());
        
        return postDTO;
    }
	
	public NewPostDTO createNewPostDTO(PostModel postModel) {
		
        NewPostDTO postDTO = new NewPostDTO();
        postDTO.setId(postModel.getId());
        postDTO.setContent(postModel.getContent());
        
        UserPostDTO userPostDTO = userFactory.createUserPostDTO(postModel.getUser());
        postDTO.setUserId(userPostDTO.getId());
        
        return postDTO;
    }
	
	public PostModel create(NewPostDTO postDTO, UserModel user) {
		
        PostModel postModel = new PostModel();
        
        if (postDTO.getImageUrl() != null && !postDTO.getImageUrl().isEmpty()) {
            String base64Data = postDTO.getImageUrl();

            if (base64Data.contains(",")) {
                base64Data = base64Data.split(",")[1];
            }

            byte[] imageBytes = Base64.getDecoder().decode(base64Data);
            postModel.setImageData(imageBytes);
        }
        
        postModel.setId(postDTO.getId());
        postModel.setContent(postDTO.getContent());
        postModel.setCreatedAt(postDTO.getCreatedAt());

        postModel.setUser(user);
        
        return postModel;
    }
}
