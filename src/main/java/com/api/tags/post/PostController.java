package com.api.tags.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.tags.category.definition.CategoryModel;
import com.api.tags.post.definition.PostModel;
import com.api.tags.post.definition.dto.PostDTO;

@RestController
@RequestMapping("/tags/posts")
public class PostController {
	
	@Autowired
	PostService postService;

    @GetMapping
    public List<PostDTO> findAll(@RequestParam int pagination, @RequestParam int items) {
        return postService.findAll(pagination, items);
    }
}
