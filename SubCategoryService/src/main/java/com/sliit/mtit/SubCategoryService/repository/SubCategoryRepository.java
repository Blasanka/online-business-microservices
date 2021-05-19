package com.sliit.mtit.SubCategoryService.repository;

import com.sliit.mtit.SubCategoryService.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    @Query(nativeQuery = true, value = "UPDATE category SET name = :name, created_by = :createdBy WHERE id = :id ")
    public void update(Long id, String name, Long createdBy);
}
