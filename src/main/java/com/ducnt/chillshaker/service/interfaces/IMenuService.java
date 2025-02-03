package com.ducnt.chillshaker.service.interfaces;

import com.ducnt.chillshaker.dto.request.menu.MenuCreationRequest;
import com.ducnt.chillshaker.dto.request.menu.MenuUpdateRequest;
import com.ducnt.chillshaker.dto.response.menu.MenuResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface IMenuService {
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    MenuResponse createMenu(MenuCreationRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    MenuResponse updateMenu(UUID id, MenuUpdateRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    boolean deleteMenu(UUID id);

    Page<MenuResponse> getAllMenu(
            String q,
            String includeProperties,
            String attribute,
            Integer pageIndex,
            Integer pageSize,
            String sort
    );

    MenuResponse getMenuById(UUID id);
}
