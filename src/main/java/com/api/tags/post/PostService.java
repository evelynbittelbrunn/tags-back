package com.api.tags.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.api.tags.category.definition.CategoryModel;
import com.api.tags.category.repository.CategoryRepository;
import com.api.tags.comment.repository.CommentRepository;
import com.api.tags.like.repository.LikeRepository;
import com.api.tags.post.definition.PostModel;
import com.api.tags.post.definition.dto.NewPostDTO;
import com.api.tags.post.definition.dto.PostDTO;
import com.api.tags.post.factory.PostDTOFactory;
import com.api.tags.post.repository.PostRepository;
import com.api.tags.postCategory.definition.PostCategoryId;
import com.api.tags.postCategory.definition.PostCategoryModel;
import com.api.tags.postCategory.repository.PostCategoryRepository;
import com.api.tags.user.definition.UserModel;
import com.api.tags.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PostService {
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    private PostDTOFactory postDTOFactory;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private PostCategoryRepository postCategoryRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private CommentRepository commentRepository;
	
	
	public NewPostDTO save(NewPostDTO post) {
		
		UserModel user = userRepository.findById(post.getUserId())
			    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		PostModel model = postDTOFactory.create(post, user);
		
		PostModel newPost = postRepository.save(model);
		
		 if (post.getCategoryIds() != null && !post.getCategoryIds().isEmpty()) {
	        for (String categoryId : post.getCategoryIds()) {
	            CategoryModel category = categoryRepository.findById(categoryId)
	                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

	            PostCategoryModel postCategory = new PostCategoryModel();
	            PostCategoryId postCategoryId = new PostCategoryId(newPost.getId(), category.getId());
	            postCategory.setId(postCategoryId);
	            postCategory.setPost(newPost);
	            postCategory.setCategory(category);

	            postCategoryRepository.save(postCategory);
	        }
	    }
		
		return postDTOFactory.createNewPostDTO(newPost);
		
	}

	public List<PostDTO> findAll(int pagination, int items, String currentUserId) {
        var posts = postRepository.findAll(PageRequest.of(pagination - 1, items));
        return posts.map(post -> postDTOFactory.create(post, currentUserId)).getContent();
    }

    public List<PostDTO> findAllByUser(int pagination, int items, String userId, String currentUserId) {
        Pageable pageable = PageRequest.of(pagination - 1, items, Sort.by(Sort.Direction.DESC, "createdAt"));
        var posts = postRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        return posts.map(post -> postDTOFactory.create(post, currentUserId)).getContent();
    }
    
    @Transactional
    public void deletePostById(String postId) {
        PostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));
        
        postCategoryRepository.deleteAllByPost(post);

        likeRepository.deleteAllByPost(post);

        commentRepository.deleteAllByPost(post);

        postRepository.delete(post);
    }
}
