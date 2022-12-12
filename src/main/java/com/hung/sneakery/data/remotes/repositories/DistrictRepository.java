package com.hung.sneakery.data.remotes.repositories;

import com.hung.sneakery.data.models.entities.City;
import com.hung.sneakery.data.models.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    District findByNameAndCity(String districtName, City city);
}
