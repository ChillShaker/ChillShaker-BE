package com.ducnt.chillshaker.service.interfaces;

import com.ducnt.chillshaker.dto.request.drink.DrinkCreationRequest;
import com.ducnt.chillshaker.dto.request.drink.DrinkUpdateRequest;
import com.ducnt.chillshaker.dto.response.drink.DrinkResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface IDrinkService {
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    DrinkResponse createDrink(DrinkCreationRequest creationRequest) throws Exception;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    DrinkResponse updateDrink(UUID id, DrinkUpdateRequest request) throws Exception;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    boolean deleteDrink(UUID id);

    Page<DrinkResponse> getAllDrinks(
            String q,
            String includeProperties,
            String attribute,
            Integer pageIndex,
            Integer pageSize,
            String sort
    );

    DrinkResponse getDrinkById(UUID id);
}
