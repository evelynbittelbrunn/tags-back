package com.api.tags.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.tags.post.definition.dto.NewPostDTO;
import com.api.tags.post.definition.dto.PostDTO;

@RestController
@RequestMapping("/tags/posts")
public class PostController {
	
	@Autowired
	PostService postService;
	
	@PostMapping("/create")
	public NewPostDTO save(@RequestBody NewPostDTO post) {
		return postService.save(post);
	}
	
    @GetMapping
    public List<PostDTO> findAll(@RequestParam int pagination, @RequestParam int items) {
        return postService.findAll(pagination, items);
    }
}
