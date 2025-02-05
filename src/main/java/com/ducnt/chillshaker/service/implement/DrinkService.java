package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.dto.request.drink.DrinkCreationRequest;
import com.ducnt.chillshaker.dto.request.drink.DrinkUpdateRequest;
import com.ducnt.chillshaker.dto.response.drink.DrinkResponse;
import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import com.ducnt.chillshaker.exception.ExistDataException;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.Drink;
import com.ducnt.chillshaker.model.DrinkCategory;
import com.ducnt.chillshaker.repository.DrinkCategoryRepository;
import com.ducnt.chillshaker.repository.DrinkRepository;
import com.ducnt.chillshaker.repository.GenericSpecification;
import com.ducnt.chillshaker.service.thirdparty.CloudinaryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class DrinkService implements com.ducnt.chillshaker.service.interfaces.IDrinkService {
    DrinkRepository drinkRepository;
    DrinkCategoryRepository drinkCategoryRepository;
    ModelMapper modelMapper;
    CloudinaryService cloudinaryService;
    GenericSpecification<Drink> drinkGenericSpecification;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public DrinkResponse createDrink(DrinkCreationRequest creationRequest) throws Exception {
        try {
            if(drinkRepository.existsDrinkByName(creationRequest.getName()))
                throw new ExistDataException("Drink is exist in database");

            var drinkCategory = drinkCategoryRepository.findById(creationRequest.getDrinkCategoryId())
                    .orElseThrow(() -> new NotFoundException("Drink category is not found"));

            Drink drink = modelMapper.map(creationRequest, Drink.class);
            drink.setDrinkCategory(drinkCategory);
            List<String> drinkImageUrls = cloudinaryService.uploadFiles(creationRequest.getFiles());
            drink.setImage(String.join(", ", drinkImageUrls));
            drink.setStatus(true);

            drinkRepository.save(drink);
            return modelMapper.map(drink, DrinkResponse.class);
        } catch (ExistDataException ex) {
            throw new ExistDataException(ex.getMessage());
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (CustomException ex) {
            throw new CustomException(ex.getErrorResponse());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public DrinkResponse updateDrink(UUID id, DrinkUpdateRequest request) throws Exception {
        try {
            Drink drink = drinkRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Drink is not found"));
            DrinkCategory drinkCategory = drinkCategoryRepository.findById(request.getDrinkCategoryId())
                    .orElseThrow(() -> new NotFoundException("Drink category is not found"));

            modelMapper.map(request, drink);

            List<String> updatedImageUrls = cloudinaryService
                    .updateFiles(request.getOldFileUrls(), request.getNewFiles());

            drink.setImage(String.join(", ", updatedImageUrls));
            drink.setDrinkCategory(drinkCategory);

            drinkRepository.save(drink);

            return modelMapper.map(drink, DrinkResponse.class);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (CustomException ex) {
            throw new CustomException(ex.getErrorResponse());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public boolean deleteDrink(UUID id) {
        try {
            Drink drink = drinkRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Drink is not found"));
            drink.setStatus(false);
            drinkRepository.save(drink);
            return true;
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @Override
    public Page<DrinkResponse> getAllDrinks(
            String q,
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


        var filters = drinkGenericSpecification.getFilters(q, includeProperties, attribute);

        long totalOfElement = drinkRepository.count();

        Page<Drink> drinkList = drinkRepository.findAll(filters, pageRequest);

        List<DrinkResponse> drinkResponses = drinkList.getContent().stream()
                .map((element) -> {
                    if(element.isStatus())
                        return modelMapper.map(element, DrinkResponse.class);
                    return null;
                })
                .toList();
        return new PageImpl<>(drinkResponses, drinkList.getPageable(), totalOfElement);
    }

    @Override
    public DrinkResponse getDrinkById(UUID id) {
        Drink drink = drinkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Drink is not found"));
        return modelMapper.map(drink, DrinkResponse.class);
    }
}
