package com.hung.sneakery.data.remotes.repositories;

import com.hung.sneakery.data.models.entities.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {
    List<Ward> findByDistrict_Id(Long id);
}
