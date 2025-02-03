package com.ducnt.chillshaker.service.interfaces;

import com.ducnt.chillshaker.dto.request.bar.BarUpdateRequest;
import com.ducnt.chillshaker.dto.response.bar.BarResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface IBarService {
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    BarResponse updateBar(UUID id, BarUpdateRequest request);

    @Transactional
    BarResponse getBarById(UUID id);
}
