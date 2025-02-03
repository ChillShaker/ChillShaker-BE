package com.ducnt.chillshaker.repository;

import com.ducnt.chillshaker.model.BarTime;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface BarTimeRepository extends JpaRepository<BarTime, UUID> {
  
}
