package com.api.tags.comment.definition;

import java.time.LocalDateTime;

import com.api.tags.post.definition.PostModel;
import com.api.tags.user.definition.UserModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "comments")
public class CommentModel {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserModel user;

	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	private PostModel post;

	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}
}
