package com.ducnt.chillshaker.controller;

import com.ducnt.chillshaker.dto.request.drink.DrinkCreationRequest;
import com.ducnt.chillshaker.dto.request.drink.DrinkUpdateRequest;
import com.ducnt.chillshaker.dto.response.common.ApiResponse;
import com.ducnt.chillshaker.dto.response.drink.DrinkResponse;
import com.ducnt.chillshaker.service.implement.DrinkService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("${api.base-url}")
public class DrinkController {
    DrinkService drinkService;

    @PostMapping("/drink")
    public ApiResponse createDrink(@Valid @ModelAttribute DrinkCreationRequest request) throws Exception {
        DrinkResponse drinkResponse = drinkService.createDrink(request);
        return ApiResponse
                .builder()
                .code(HttpStatus.CREATED.value())
                .message("Created account successfully")
                .data(drinkResponse)
                .build();
    }

    @PutMapping("/drink/{id}")
    public ApiResponse updateDrink(@PathVariable("id")UUID id, @Valid @ModelAttribute DrinkUpdateRequest request) throws Exception {
        DrinkResponse drinkResponse = drinkService.updateDrink(id, request);
        return ApiResponse
                .builder()
                .code(HttpStatus.CREATED.value())
                .message("Created account successfully")
                .data(drinkResponse)
                .build();
    }

    @PermitAll
    @GetMapping("/drinks")
    public ApiResponse getAllDrink(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(required = false, defaultValue = "") String includeProperties,
            @RequestParam(required = false, defaultValue = "name") String attribute,
            @RequestParam(required = false, defaultValue = "0") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "4") Integer pageSize,
            @RequestParam(required = false, defaultValue = "") String sort
    ) {
        Page<DrinkResponse> drinkResponsePage = drinkService
                .getAllDrinks(q, includeProperties, attribute, pageIndex, pageSize, sort);
        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Data loaded")
                .data(drinkResponsePage)
                .build();
    }

    @PermitAll
    @GetMapping("/drink/{id}")
    public ApiResponse getDrinkById(@PathVariable("id") UUID id) {
        DrinkResponse drinkResponse = drinkService.getDrinkById(id);
        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Data loaded")
                .data(drinkResponse)
                .build();
    }

    @DeleteMapping("/drink/{id}")
    public ApiResponse deleteDrink(@PathVariable("id") UUID id) {
        boolean dataResponse = drinkService.deleteDrink(id);
        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Drink is deleted successful")
                .data(dataResponse)
                .build();
    }
}
