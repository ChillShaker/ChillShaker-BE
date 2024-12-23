package com.ducnt.chillshaker.repository;

import com.ducnt.chillshaker.model.Bar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BarRepository extends JpaRepository<Bar, UUID> {
}
