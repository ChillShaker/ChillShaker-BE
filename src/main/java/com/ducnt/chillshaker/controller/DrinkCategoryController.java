package com.ducnt.chillshaker.controller;

import com.ducnt.chillshaker.dto.request.drinkCategory.DrinkCategoryCreationRequest;
import com.ducnt.chillshaker.dto.request.drinkCategory.DrinkCategoryUpdationRequest;
import com.ducnt.chillshaker.dto.response.common.ApiResponse;
import com.ducnt.chillshaker.service.implement.DrinkCategoryService;
import jakarta.annotation.security.PermitAll;
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
public class DrinkCategoryController {
    DrinkCategoryService drinkCategoryService;

    @PostMapping("/drink-category")
    public ApiResponse createDrinkCategory(@RequestBody DrinkCategoryCreationRequest request) {
        var responseData = drinkCategoryService.createDrinkCategory(request);
        return ApiResponse
                .builder()
                .message("Created drink category success")
                .code(HttpStatus.CREATED.value())
                .data(responseData)
                .build();
    }

    @PutMapping("/drink-category/{id}")
    public ApiResponse updateDrinkCategory(@PathVariable("id")UUID id,
                                           @RequestBody DrinkCategoryUpdationRequest request) {
        var responseData = drinkCategoryService.updateDrinkCategory(id, request);
        return ApiResponse
                .builder()
                .message("Updated drink category success")
                .code(HttpStatus.OK.value())
                .data(responseData)
                .build();
    }

    @DeleteMapping("/drink-category/{id}")
    public ApiResponse deleteDrinkCategory(@PathVariable("id") UUID id) {
        var responseData = drinkCategoryService.deleteDrinkCategory(id);
        return ApiResponse
                .builder()
                .message("Deleted drink category success")
                .code(HttpStatus.OK.value())
                .data(responseData)
                .build();
    }

    @GetMapping("/drink-categories")
    public ApiResponse getAllDrinkCategory(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(required = false, defaultValue = "") String includeProperties,
            @RequestParam(required = false, defaultValue = "name") String attribute,
            @RequestParam(required = false, defaultValue = "0") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "") String sort
    ) {
        var responseData = drinkCategoryService.getAllDrinkCategory(q, includeProperties, attribute, pageIndex, pageSize, sort);
        return ApiResponse
                .builder()
                .message("Data loaded")
                .code(HttpStatus.OK.value())
                .data(responseData)
                .build();
    }

    @GetMapping("/drink-category/{id}")
    public ApiResponse getAllDrinkCategory(@PathVariable("id") UUID id) {
        var responseData = drinkCategoryService.getById(id);
        return ApiResponse
                .builder()
                .message("Data loaded")
                .code(HttpStatus.OK.value())
                .data(responseData)
                .build();
    }
}
