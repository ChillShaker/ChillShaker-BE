package com.ducnt.chillshaker.controller;

import com.ducnt.chillshaker.dto.request.menu.MenuCreationRequest;
import com.ducnt.chillshaker.dto.request.menu.MenuUpdateRequest;
import com.ducnt.chillshaker.dto.response.common.ApiResponse;
import com.ducnt.chillshaker.service.implement.MenuService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${api.base-url}")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MenuController {
    MenuService menuService;

    @PostMapping("/menu")
    public ApiResponse createMenu(@Valid @ModelAttribute MenuCreationRequest request) {
        var dataResponse = menuService.createMenu(request);
        return ApiResponse
                .builder()
                .code(HttpStatus.CREATED.value())
                .message("Created menu success")
                .data(dataResponse)
                .build();
    }

    @PutMapping("/menu/{id}")
    public ApiResponse updateMenu(@PathVariable("id")UUID id, @Valid @ModelAttribute MenuUpdateRequest request) {
        var dataResponse = menuService.updateMenu(id, request);
        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Updated menu success")
                .data(dataResponse)
                .build();
    }

    @DeleteMapping("/menu/{id}")
    public ApiResponse deleteMenu(@PathVariable("id")UUID id) {
        var dataResponse = menuService.deleteMenu(id);
        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Deleted menu success")
                .data(dataResponse)
                .build();
    }

    @GetMapping("/menus")
    public ApiResponse getAllMenus(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(required = false, defaultValue = "") String includeProperties,
            @RequestParam(required = false, defaultValue = "name") String attribute,
            @RequestParam(required = false, defaultValue = "0") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "") String sort
    ) {
        var dataResponse = menuService.getAllMenu(q, includeProperties, attribute, pageIndex, pageSize, sort);
        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Data load success")
                .data(dataResponse)
                .build();
    }

    @GetMapping("/menu/{id}")
    public ApiResponse getMenuById(@PathVariable("id") UUID id) {
        var dataResponse = menuService.getMenuById(id);
        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Data load success")
                .data(dataResponse)
                .build();
    }
}
