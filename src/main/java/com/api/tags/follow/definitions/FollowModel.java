package com.api.tags.follow.definitions;

import com.api.tags.user.definition.UserModel;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "follows")
public class FollowModel {
	@EmbeddedId
    private FollowId id;

    @ManyToOne
    @MapsId("followerId")
    @JoinColumn(name = "follower_id")
    private UserModel follower; 

    @ManyToOne
    @MapsId("followedId")
    @JoinColumn(name = "followed_id")
    private UserModel followed; 
}
