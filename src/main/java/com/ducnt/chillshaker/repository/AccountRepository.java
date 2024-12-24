package com.ducnt.chillshaker.repository;

import com.ducnt.chillshaker.model.Account;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    boolean existsByEmail(String email);
    Optional<Account> findByEmail(String email);
    //Optional<Account> findByEmail(String email, @Nullable Specification<Account> filters);
}
