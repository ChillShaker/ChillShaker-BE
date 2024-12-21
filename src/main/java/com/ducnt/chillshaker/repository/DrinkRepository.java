package com.ducnt.chillshaker.repository;

import com.ducnt.chillshaker.model.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, Integer>  {
}
