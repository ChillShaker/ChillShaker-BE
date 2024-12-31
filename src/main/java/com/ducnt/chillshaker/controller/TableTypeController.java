package com.ducnt.chillshaker.controller;

import com.ducnt.chillshaker.dto.request.tableType.TableTypeCreationRequest;
import com.ducnt.chillshaker.dto.request.tableType.TableTypeUpdationRequest;
import com.ducnt.chillshaker.dto.response.common.ApiResponse;
import com.ducnt.chillshaker.dto.response.tableType.TableTypeResponse;
import com.ducnt.chillshaker.service.implement.TableTypeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${api.base-url}")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TableTypeController {
    TableTypeService tableTypeService;

    @PostMapping("/table-type")
    public ApiResponse createTableType(@Valid @ModelAttribute TableTypeCreationRequest request) {
        TableTypeResponse responseData = tableTypeService.createTableType(request);
        return ApiResponse
                .builder()
                .code(HttpStatus.CREATED.value())
                .message("Created table type success")
                .data(responseData)
                .build();
    }

    @PutMapping("/table-type/{id}")
    public ApiResponse updateTableType(@PathVariable("id") UUID id,
                                       @Valid @ModelAttribute TableTypeUpdationRequest request) {
        TableTypeResponse responseData = tableTypeService.updateTableType(id, request);
        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Updated table type success")
                .data(responseData)
                .build();
    }

    @DeleteMapping("/table-type/{id}")
    public ApiResponse deleteTableType(@PathVariable("id") UUID id) {
        boolean responseData = tableTypeService.deleteTableType(id);
        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Deleted table type success")
                .data(responseData)
                .build();
    }

    @GetMapping("/table-type/{id}")
    public ApiResponse getTableTypeById(@PathVariable("id") UUID id) {
        TableTypeResponse responseData = tableTypeService.getTableTypeById(id);
        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Data loaded success")
                .data(responseData)
                .build();
    }

    @GetMapping("/table-types")
    public ApiResponse getAllTableType(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(required = false, defaultValue = "") String includeProperties,
            @RequestParam(required = false, defaultValue = "name") String attribute,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "0") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        Page<TableTypeResponse> responseData = tableTypeService.getAllTableTypes(q, includeProperties, attribute,
                sort, pageIndex, pageSize);

        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Data loaded success")
                .data(responseData)
                .build();
    }
}
