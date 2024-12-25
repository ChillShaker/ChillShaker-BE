package com.ducnt.chillshaker.repository;

import com.ducnt.chillshaker.model.DrinkCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface DrinkCategoryRepository extends JpaRepository<DrinkCategory, UUID>, JpaSpecificationExecutor<DrinkCategory> {
    boolean existsByName(String name);
}
