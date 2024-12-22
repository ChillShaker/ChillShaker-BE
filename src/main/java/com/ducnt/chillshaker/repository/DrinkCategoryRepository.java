package com.ducnt.chillshaker.repository;

import com.ducnt.chillshaker.model.DrinkCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DrinkCategoryRepository extends JpaRepository<DrinkCategory, UUID> {
}
