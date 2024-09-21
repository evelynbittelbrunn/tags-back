package com.api.tags.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.tags.auth.definition.dto.AuthenticationDTO;
import com.api.tags.auth.definition.dto.LoginResponseDTO;
import com.api.tags.auth.definition.dto.ResgisterDTO;
import com.api.tags.infra.security.TokenService;
import com.api.tags.user.definition.UserModel;
import com.api.tags.user.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthorizationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		var userNamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
		var auth = this.authenticationManager.authenticate(userNamePassword);

		var token = tokenService.generateToken((UserModel)auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}

	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Valid ResgisterDTO data) {
		if (this.repository.findByEmail(data.getEmail()) != null)
			return ResponseEntity.badRequest().build();
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
		
		UserModel newUser = new UserModel(data.getEmail(), data.getName(), encryptedPassword, data.getRole());
		
		this.repository.save(newUser);
		
		return ResponseEntity.ok().build();
	}
}
