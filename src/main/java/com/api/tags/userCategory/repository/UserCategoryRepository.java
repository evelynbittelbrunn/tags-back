package com.api.tags.userCategory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.tags.userCategory.definition.UserCategoryId;
import com.api.tags.userCategory.definition.UserCategoryModel;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategoryModel, UserCategoryId>  {
	List<UserCategoryModel> findByUserId(String userId);
}
