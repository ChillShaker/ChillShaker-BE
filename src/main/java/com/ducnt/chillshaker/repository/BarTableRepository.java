package com.ducnt.chillshaker.repository;

import com.ducnt.chillshaker.model.BarTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BarTableRepository extends JpaRepository<BarTable, UUID>, JpaSpecificationExecutor<BarTable> {
    boolean existsByName(String name);
    
    Page<BarTable> findAllByIsDeletedFalse(Pageable pageable);
    
    @Override
    Page<BarTable> findAll(Specification<BarTable> spec, Pageable pageable);
}
