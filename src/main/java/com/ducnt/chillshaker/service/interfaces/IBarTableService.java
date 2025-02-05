package com.ducnt.chillshaker.service.interfaces;

import com.ducnt.chillshaker.dto.request.barTable.BarTableCreationRequest;
import com.ducnt.chillshaker.dto.request.barTable.BarTableStatusRequest;
import com.ducnt.chillshaker.dto.request.barTable.BarTableUpdateRequest;
import com.ducnt.chillshaker.dto.response.barTable.BarTableManagementResponse;
import com.ducnt.chillshaker.dto.response.barTable.BarTableResponse;
import com.ducnt.chillshaker.dto.response.barTable.BarTableStatusResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface IBarTableService {
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    BarTableResponse createBarTable(BarTableCreationRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    BarTableResponse updateBarTable(UUID id, BarTableUpdateRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    boolean deleteBarTableById(UUID id);

    @PreAuthorize("hasRole('CUSTOMER')")
    Page<BarTableResponse> getAllBarTableForCustomer(
            String q,
            String includeProperties,
            String attribute,
            String sort,
            Integer pageIndex,
            Integer pageSize
    );

    @PreAuthorize("hasRole('ADMIN')")
    Page<BarTableManagementResponse> getAllBarTableForAdmin(
            String q,
            String includeProperties,
            String attribute,
            String sort,
            Integer pageIndex,
            Integer pageSize
    );

    BarTableResponse getBarTableById(UUID id);

    List<BarTableResponse> getBarTableByDateTime(LocalDate bookingDate, LocalTime bookingTime);

    BarTableStatusResponse getStatusBarTable(BarTableStatusRequest request);

    BarTableStatusResponse setStatusBarTable(BarTableStatusRequest request);
}
