package com.api.tags.user.definition;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.tags.follow.definitions.FollowModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "users")
@Entity(name = "users")
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserModel implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	private String email;
	
	private String name;
	
	private String password;
	
	private RoleEnum role;
	
	private byte[] profilePicture;
	
	private String bio;
	
	private LocalDateTime createdAt;
	
	@OneToMany(mappedBy = "follower")
    private Set<FollowModel> following;

    @OneToMany(mappedBy = "followed")
    private Set<FollowModel> followers;
	
	public UserModel(String email, String name, String password, RoleEnum role) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.role = role;
		this.createdAt = LocalDateTime.now();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(this.role == RoleEnum.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getUsername() {
		return email;
	}
}
