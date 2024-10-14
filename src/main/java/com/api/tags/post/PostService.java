package com.api.tags.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.api.tags.post.definition.PostModel;
import com.api.tags.post.definition.dto.PostDTO;
import com.api.tags.post.factory.PostDTOFactory;
import com.api.tags.post.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
    private PostDTOFactory postDTOFactory;

	public List<PostDTO> findAll(int pagination, int items) {
		
		Page<PostModel> posts = postRepository.findAll(PageRequest.of(pagination, items));
		
		return posts.map(postDTOFactory::create).getContent();
	}
}
