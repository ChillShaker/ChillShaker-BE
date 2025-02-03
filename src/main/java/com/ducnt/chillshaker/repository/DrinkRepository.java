package com.ducnt.chillshaker.repository;

import com.ducnt.chillshaker.model.Drink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, UUID>, JpaSpecificationExecutor<Drink> {
    boolean existsDrinkByName(String name);

    Page<Drink> findAllByStatus(@Nullable Specification<Drink> spec, Pageable pageable, boolean status);
}
