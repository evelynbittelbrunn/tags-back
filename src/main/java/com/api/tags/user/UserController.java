package com.api.tags.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.tags.user.definition.dto.UserProfileDTO;
import com.api.tags.user.definition.dto.UserProfileEditDTO;
import com.api.tags.user.definition.dto.UserSearchDTO;

@RestController
@RequestMapping("/tags/users")
public class UserController {
	
	@Autowired
    private UserService userService;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileDTO> getUserNameAndBio(
    		@PathVariable String userId, 
    		@RequestParam String currentUserId
    ) {
    	UserProfileDTO userDTO = userService.getUserNameAndBio(userId, currentUserId);
        return ResponseEntity.ok(userDTO);
    }
    
    @PutMapping("/profile/{userId}")
    public ResponseEntity<UserProfileEditDTO> updateUserProfile(
            @PathVariable String userId, 
            @RequestBody UserProfileEditDTO userProfileDTO
    	) {
        
    	UserProfileEditDTO updatedProfile = userService.updateUserProfile(userId, userProfileDTO);
        return ResponseEntity.ok(updatedProfile);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<UserSearchDTO>> searchUsers(
        @RequestParam int pagination,
        @RequestParam int items,
        @RequestParam String name
    ) {
        Pageable pageable = PageRequest.of(pagination - 1, items);
        List<UserSearchDTO> results = userService.searchUsers(name, pageable);
        return ResponseEntity.ok(results);
    }
}
