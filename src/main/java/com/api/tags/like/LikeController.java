package com.api.tags.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/like")
public class LikeController {
	
	@Autowired
    private LikeService likeService;

	public ResponseEntity<String> toggleLike(
            @RequestParam String userId,
        try {
            likeService.toggleLike(userId, postId);
            return ResponseEntity.ok("Ação de curtida realizada com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Usuário ou post não encontrado");
        }
    }
}
