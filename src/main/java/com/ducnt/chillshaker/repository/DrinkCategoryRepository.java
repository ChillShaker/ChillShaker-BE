package com.ducnt.chillshaker.repository;

import com.ducnt.chillshaker.model.DrinkCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkCategoryRepository extends JpaRepository<DrinkCategory, Integer> {
}
