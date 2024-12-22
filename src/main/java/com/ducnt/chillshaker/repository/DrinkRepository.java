package com.ducnt.chillshaker.repository;

import com.ducnt.chillshaker.model.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DrinkRepository extends JpaRepository<Drink, UUID>  {
}
