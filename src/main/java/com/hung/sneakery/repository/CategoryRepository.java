package com.hung.sneakery.repository;

import com.hung.sneakery.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String Name);
}
