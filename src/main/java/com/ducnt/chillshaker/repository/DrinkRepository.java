package com.ducnt.chillshaker.repository;

import com.ducnt.chillshaker.model.Drink;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, UUID>, JpaSpecificationExecutor<Drink> {
    boolean existsDrinkByName(String name);

    @EntityGraph(attributePaths = {"drinkCategory"})
    @Override
    List<Drink> findAll();
}
