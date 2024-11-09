package com.api.tags.follow;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<String> toggleFollow(@RequestParam String followerId, @RequestParam String followedId) {
        try {
            followService.toggleFollow(followerId, followedId);
            return ResponseEntity.ok("Ação realizada com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
    }
}
