package com.api.tags.post.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.tags.post.definition.PostModel;
import com.api.tags.post.definition.dto.PostDTO;
import com.api.tags.user.definition.dto.UserPostDTO;
import com.api.tags.user.factory.UserDTOFactory;

@Component
public class PostDTOFactory {
	
	@Autowired
    private UserDTOFactory userFactory;
	
	public PostDTO create(PostModel postModel) {
		
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postModel.getId());
        postDTO.setContent(postModel.getContent());
        
        UserPostDTO userPostDTO = userFactory.createUserPostDTO(postModel.getUser());
        postDTO.setUser(userPostDTO);
        
        return postDTO;
    }
}
