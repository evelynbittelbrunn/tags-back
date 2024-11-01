package com.api.tags.post;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.api.tags.post.definition.PostModel;
import com.api.tags.post.definition.dto.NewPostDTO;
import com.api.tags.post.definition.dto.PostDTO;
import com.api.tags.post.factory.PostDTOFactory;
import com.api.tags.post.repository.PostRepository;
import com.api.tags.user.definition.UserModel;
import com.api.tags.user.repository.UserRepository;

@Service
public class PostService {
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    private PostDTOFactory postDTOFactory;
	
	
	public NewPostDTO save(NewPostDTO post) {
		
		UserModel user = userRepository.findById(post.getUserId())
			    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		PostModel model = postDTOFactory.create(post, user);
		
		PostModel newPost = postRepository.save(model);
		
		return postDTOFactory.createNewPostDTO(newPost);
		
	}

	public List<PostDTO> findAll(int pagination, int items) {
		
		Page<PostModel> posts = postRepository.findAll(PageRequest.of(pagination - 1, items));
		
		return posts.map(postDTOFactory::create).getContent();
	}
}
