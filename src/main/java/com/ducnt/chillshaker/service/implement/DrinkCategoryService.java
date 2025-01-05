package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.dto.request.drinkCategory.DrinkCategoryCreationRequest;
import com.ducnt.chillshaker.dto.request.drinkCategory.DrinkCategoryUpdateRequest;
import com.ducnt.chillshaker.dto.response.drinkCategory.DrinkCategoryResponse;
import com.ducnt.chillshaker.exception.ExistDataException;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.DrinkCategory;
import com.ducnt.chillshaker.repository.DrinkCategoryRepository;
import com.ducnt.chillshaker.repository.GenericSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DrinkCategoryService {
    DrinkCategoryRepository drinkCategoryRepository;
    ModelMapper modelMapper;
    GenericSpecification<DrinkCategory> genericSpecification;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public DrinkCategoryResponse createDrinkCategory(DrinkCategoryCreationRequest request) {
        if(drinkCategoryRepository.existsByName(request.getName()))
            throw new ExistDataException("Drink Category name is existed");

        DrinkCategory drinkCategory = modelMapper.map(request, DrinkCategory.class);
        drinkCategoryRepository.save(drinkCategory);
        return modelMapper.map(drinkCategory, DrinkCategoryResponse.class);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public DrinkCategoryResponse updateDrinkCategory(UUID id, DrinkCategoryUpdateRequest request) {
        DrinkCategory drinkCategory = drinkCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Drink Category is not found"));

        modelMapper.map(request, drinkCategory);
        drinkCategoryRepository.save(drinkCategory);
        return modelMapper.map(drinkCategory, DrinkCategoryResponse.class);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteDrinkCategory(UUID id) {
        DrinkCategory drinkCategory = drinkCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Drink Category is not found"));
        drinkCategoryRepository.delete(drinkCategory);
        return true;
    }

    public Page<DrinkCategoryResponse> getAllDrinkCategory(String q,
                                                           String includeProperties,
                                                           String attribute,
                                                           Integer pageIndex,
                                                           Integer pageSize,
                                                           String sort
    ) {


        PageRequest pageRequest = PageRequest.of(
                pageIndex > 0 ? pageIndex - 1 : 0,
                pageSize,
                sort.equals("desc") ? Sort.by(Sort.Direction.DESC, attribute) : Sort.by(Sort.Direction.ASC, attribute)
        );
        Specification<DrinkCategory> filters = genericSpecification.getFilters(q, includeProperties, attribute);

        Page<DrinkCategory> drinkCategoryPage = drinkCategoryRepository.findAll(filters, pageRequest);

        List<DrinkCategoryResponse> drinkCategoryResponses = drinkCategoryPage.stream().map(drinkCategory
                -> modelMapper.map(drinkCategory, DrinkCategoryResponse.class)).toList();

        return new PageImpl<>(drinkCategoryResponses, drinkCategoryPage.getPageable(),
                drinkCategoryPage.getNumberOfElements());
    }

    public DrinkCategoryResponse getById(UUID id) {
        DrinkCategory drinkCategory = drinkCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Drink Category is not found"));
        return modelMapper.map(drinkCategory, DrinkCategoryResponse.class);
    }
}
