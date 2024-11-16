package com.api.tags.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.tags.comment.definition.CommentModel;
import com.api.tags.comment.definition.dto.CommentDTO;

@RestController
@RequestMapping("/tags/comments")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@PostMapping
    public ResponseEntity<String> createComment(@RequestBody CommentDTO commentDTO) {
		try {
	        commentService.createComment(commentDTO);
	        return ResponseEntity.ok("Comentário criado com sucesso");
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body("Erro: Usuário ou post não encontrado");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar o comentário");
	    }
    }
}
