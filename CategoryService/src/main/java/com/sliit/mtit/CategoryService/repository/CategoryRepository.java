package com.sliit.mtit.CategoryService.repository;

import com.sliit.mtit.CategoryService.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(nativeQuery = true, value = "UPDATE category SET name = :name, created_by = :createdBy WHERE id = :id ")
    public void update(Long id, String name, Long createdBy);
}
