package com.ducnt.chillshaker.repository;

import com.ducnt.chillshaker.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findAllByName(Iterable<String> roles);
}
