package com.api.tags.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.tags.user.definition.UserModel;

public interface UserRepository extends JpaRepository<UserModel, String> {
	UserDetails findByEmail(String email);
}
