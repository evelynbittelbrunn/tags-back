package com.api.tags.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.tags.comment.definition.dto.CommentDTO;
import com.api.tags.comment.definition.dto.NewCommentDTO;

@RestController
@RequestMapping("/tags/comments")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@PostMapping
	public ResponseEntity<CommentDTO> createComment(@RequestBody NewCommentDTO commentDTO) {
	    try {
	        CommentDTO createdComment = commentService.createComment(commentDTO);	        
	        return ResponseEntity.ok(createdComment);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(null);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	@GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable String postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}
