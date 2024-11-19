package com.api.tags.follow;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
public class FollowController {
	
	@Autowired
    private FollowService followService;

	@PostMapping("/follow")
	public ResponseEntity<Map<String, Object>> toggleFollow(@RequestParam String followerId, @RequestParam String followedId) {
	    try {
	        boolean isFollowing = followService.toggleFollow(followerId, followedId);

	        Map<String, Object> response = new HashMap<>();
	        response.put("isFollowing", isFollowing);
	        response.put("message", isFollowing ? "Agora você está seguindo o usuário." : "Você deixou de seguir o usuário.");

	        return ResponseEntity.ok(response);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(Map.of("message", "Usuário não encontrado"));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Erro ao processar a solicitação"));
	    }
	}
}
