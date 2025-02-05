package com.ducnt.chillshaker.service.interfaces;

import com.ducnt.chillshaker.dto.request.tableType.TableTypeCreationRequest;
import com.ducnt.chillshaker.dto.request.tableType.TableTypeUpdateRequest;
import com.ducnt.chillshaker.dto.response.tableType.TableTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ITableTypeService {
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    TableTypeResponse createTableType(TableTypeCreationRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    TableTypeResponse updateTableType(UUID id, TableTypeUpdateRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    boolean deleteTableType(UUID id);

    Page<TableTypeResponse> getAllTableTypes(
            String q,
            String includeProperties,
            String attribute,
            String sort,
            Integer pageIndex,
            Integer pageSize
    );

    TableTypeResponse getTableTypeById(UUID id);
}
