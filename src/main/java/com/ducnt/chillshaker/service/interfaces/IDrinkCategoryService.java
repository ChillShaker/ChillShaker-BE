package com.ducnt.chillshaker.service.interfaces;

import com.ducnt.chillshaker.dto.request.drinkCategory.DrinkCategoryCreationRequest;
import com.ducnt.chillshaker.dto.request.drinkCategory.DrinkCategoryUpdateRequest;
import com.ducnt.chillshaker.dto.response.drinkCategory.DrinkCategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface IDrinkCategoryService {
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    DrinkCategoryResponse createDrinkCategory(DrinkCategoryCreationRequest request);

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    DrinkCategoryResponse updateDrinkCategory(UUID id, DrinkCategoryUpdateRequest request);

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    boolean deleteDrinkCategory(UUID id);

    Page<DrinkCategoryResponse> getAllDrinkCategory(String q,
                                                    String includeProperties,
                                                    String attribute,
                                                    Integer pageIndex,
                                                    Integer pageSize,
                                                    String sort
    );

    DrinkCategoryResponse getById(UUID id);
}
