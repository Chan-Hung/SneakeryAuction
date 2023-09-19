package com.hung.sneakery.data.remotes.repositories;

import com.hung.sneakery.data.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String Name);
}
