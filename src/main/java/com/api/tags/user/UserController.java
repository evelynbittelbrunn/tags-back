package com.api.tags.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.tags.user.definition.dto.UserProfileDTO;

@RestController
@RequestMapping("/tags/users")
public class UserController {
	 @Autowired
    private UserService userService;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileDTO> getUserNameAndBio(@PathVariable String userId) {
        UserProfileDTO userDTO = userService.getUserNameAndBio(userId);
        return ResponseEntity.ok(userDTO);
    }
}
