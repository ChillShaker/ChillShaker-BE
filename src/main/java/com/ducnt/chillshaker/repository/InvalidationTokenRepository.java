package com.ducnt.chillshaker.repository;

import com.ducnt.chillshaker.model.InvalidationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvalidationTokenRepository extends JpaRepository<InvalidationToken, UUID> {
}
