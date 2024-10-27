package com.api.tags.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.tags.category.definition.CategoryModel;
import com.api.tags.category.definition.dto.CategoryWithSavedStatusDTO;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, String> {
	
	@Query("SELECT new com.api.tags.category.definition.dto.CategoryWithSavedStatusDTO(c.id, c.description, " +
		       "(CASE WHEN uc.category IS NOT NULL THEN true ELSE false END)) " +
		       "FROM CategoryModel c " +
		       "LEFT JOIN UserCategoryModel uc ON c.id = uc.category.id AND uc.user.id = :userId")
	List<CategoryWithSavedStatusDTO> findAllWithSavedStatus(@Param("userId") String userId);
}
