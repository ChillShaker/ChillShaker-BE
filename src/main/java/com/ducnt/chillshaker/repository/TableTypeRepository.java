package com.ducnt.chillshaker.repository;

import com.ducnt.chillshaker.model.TableType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TableTypeRepository extends JpaRepository<TableType, UUID>, JpaSpecificationExecutor<TableType> {
    boolean existsByName(String name);
}
