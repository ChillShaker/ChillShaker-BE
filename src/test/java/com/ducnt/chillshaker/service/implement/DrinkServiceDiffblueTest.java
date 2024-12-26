package com.ducnt.chillshaker.service.implement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ducnt.chillshaker.dto.request.drink.DrinkCreationRequest;
import com.ducnt.chillshaker.dto.request.drink.DrinkUpdationRequest;
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
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {DrinkService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DrinkServiceDiffblueTest {
    @MockBean
    private CloudinaryService cloudinaryService;

    @MockBean
    private DrinkCategoryRepository drinkCategoryRepository;

    @MockBean
    private DrinkRepository drinkRepository;

    @Autowired
    private DrinkService drinkService;

    @MockBean
    private GenericSpecification<Drink> genericSpecification;

    @MockBean
    private ModelMapper modelMapper;

    /**
     * Method under test: {@link DrinkService#createDrink(DrinkCreationRequest)}
     */
    @Test
    void testCreateDrink() throws Exception {
        // Arrange
        when(drinkRepository.existsDrinkByName(Mockito.<String>any())).thenReturn(true);

        // Act and Assert
        assertThrows(ExistDataException.class, () -> drinkService.createDrink(new DrinkCreationRequest()));
        verify(drinkRepository).existsDrinkByName(isNull());
    }

    /**
     * Method under test: {@link DrinkService#createDrink(DrinkCreationRequest)}
     */
    @Test
    void testCreateDrink2() throws Exception {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");

        Drink drink = new Drink();
        drink.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink.setDescription("The characteristics of someone or something");
        drink.setDrinkCategory(drinkCategory);
        drink.setId(UUID.randomUUID());
        drink.setImage("Image");
        drink.setMenus(new ArrayList<>());
        drink.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink.setName("Name");
        drink.setPrice(10.0d);
        drink.setStatus(true);
        when(drinkRepository.save(Mockito.<Drink>any())).thenReturn(drink);
        when(drinkRepository.existsDrinkByName(Mockito.<String>any())).thenReturn(false);

        DrinkCategory drinkCategory2 = new DrinkCategory();
        drinkCategory2.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setDescription("The characteristics of someone or something");
        drinkCategory2.setDrinks(new ArrayList<>());
        drinkCategory2.setId(UUID.randomUUID());
        drinkCategory2.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setName("Name");
        Optional<DrinkCategory> ofResult = Optional.of(drinkCategory2);
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        DrinkCategory drinkCategory3 = new DrinkCategory();
        drinkCategory3.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory3.setDescription("The characteristics of someone or something");
        drinkCategory3.setDrinks(new ArrayList<>());
        drinkCategory3.setId(UUID.randomUUID());
        drinkCategory3.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory3.setName("Name");

        Drink drink2 = new Drink();
        drink2.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink2.setDescription("The characteristics of someone or something");
        drink2.setDrinkCategory(drinkCategory3);
        drink2.setId(UUID.randomUUID());
        drink2.setImage("Image");
        drink2.setMenus(new ArrayList<>());
        drink2.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink2.setName("Name");
        drink2.setPrice(10.0d);
        drink2.setStatus(true);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Drink>>any())).thenReturn(drink2);
        when(cloudinaryService.uploadFiles(Mockito.<List<MultipartFile>>any())).thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(Exception.class, () -> drinkService.createDrink(new DrinkCreationRequest()));
        verify(drinkRepository).existsDrinkByName(isNull());
        verify(cloudinaryService).uploadFiles(isNull());
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
        verify(drinkCategoryRepository).findById(isNull());
        verify(drinkRepository).save(isA(Drink.class));
    }

    /**
     * Method under test: {@link DrinkService#createDrink(DrinkCreationRequest)}
     */
    @Test
    void testCreateDrink3() throws Exception {
        // Arrange
        when(drinkRepository.existsDrinkByName(Mockito.<String>any())).thenReturn(false);

        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");
        Optional<DrinkCategory> ofResult = Optional.of(drinkCategory);
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        DrinkCategory drinkCategory2 = new DrinkCategory();
        drinkCategory2.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setDescription("The characteristics of someone or something");
        drinkCategory2.setDrinks(new ArrayList<>());
        drinkCategory2.setId(UUID.randomUUID());
        drinkCategory2.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setName("Name");

        Drink drink = new Drink();
        drink.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink.setDescription("The characteristics of someone or something");
        drink.setDrinkCategory(drinkCategory2);
        drink.setId(UUID.randomUUID());
        drink.setImage("Image");
        drink.setMenus(new ArrayList<>());
        drink.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink.setName("Name");
        drink.setPrice(10.0d);
        drink.setStatus(true);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Drink>>any())).thenReturn(drink);
        when(cloudinaryService.uploadFiles(Mockito.<List<MultipartFile>>any()))
                .thenThrow(new ExistDataException("An error occurred"));

        // Act and Assert
        assertThrows(ExistDataException.class, () -> drinkService.createDrink(new DrinkCreationRequest()));
        verify(drinkRepository).existsDrinkByName(isNull());
        verify(cloudinaryService).uploadFiles(isNull());
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(drinkCategoryRepository).findById(isNull());
    }

    /**
     * Method under test: {@link DrinkService#createDrink(DrinkCreationRequest)}
     */
    @Test
    void testCreateDrink4() throws Exception {
        // Arrange
        when(drinkRepository.existsDrinkByName(Mockito.<String>any())).thenReturn(false);

        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");
        Optional<DrinkCategory> ofResult = Optional.of(drinkCategory);
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        DrinkCategory drinkCategory2 = new DrinkCategory();
        drinkCategory2.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setDescription("The characteristics of someone or something");
        drinkCategory2.setDrinks(new ArrayList<>());
        drinkCategory2.setId(UUID.randomUUID());
        drinkCategory2.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setName("Name");

        Drink drink = new Drink();
        drink.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink.setDescription("The characteristics of someone or something");
        drink.setDrinkCategory(drinkCategory2);
        drink.setId(UUID.randomUUID());
        drink.setImage("Image");
        drink.setMenus(new ArrayList<>());
        drink.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink.setName("Name");
        drink.setPrice(10.0d);
        drink.setStatus(true);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Drink>>any())).thenReturn(drink);
        when(cloudinaryService.uploadFiles(Mockito.<List<MultipartFile>>any()))
                .thenThrow(new NotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(NotFoundException.class, () -> drinkService.createDrink(new DrinkCreationRequest()));
        verify(drinkRepository).existsDrinkByName(isNull());
        verify(cloudinaryService).uploadFiles(isNull());
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(drinkCategoryRepository).findById(isNull());
    }

    /**
     * Method under test: {@link DrinkService#createDrink(DrinkCreationRequest)}
     */
    @Test
    void testCreateDrink5() throws Exception {
        // Arrange
        when(drinkRepository.existsDrinkByName(Mockito.<String>any())).thenReturn(false);

        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");
        Optional<DrinkCategory> ofResult = Optional.of(drinkCategory);
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        DrinkCategory drinkCategory2 = new DrinkCategory();
        drinkCategory2.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setDescription("The characteristics of someone or something");
        drinkCategory2.setDrinks(new ArrayList<>());
        drinkCategory2.setId(UUID.randomUUID());
        drinkCategory2.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setName("Name");

        Drink drink = new Drink();
        drink.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink.setDescription("The characteristics of someone or something");
        drink.setDrinkCategory(drinkCategory2);
        drink.setId(UUID.randomUUID());
        drink.setImage("Image");
        drink.setMenus(new ArrayList<>());
        drink.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink.setName("Name");
        drink.setPrice(10.0d);
        drink.setStatus(true);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Drink>>any())).thenReturn(drink);
        when(cloudinaryService.uploadFiles(Mockito.<List<MultipartFile>>any()))
                .thenThrow(new CustomException(ErrorResponse.FULLNAME_INVALID));

        // Act and Assert
        assertThrows(CustomException.class, () -> drinkService.createDrink(new DrinkCreationRequest()));
        verify(drinkRepository).existsDrinkByName(isNull());
        verify(cloudinaryService).uploadFiles(isNull());
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(drinkCategoryRepository).findById(isNull());
    }

    /**
     * Method under test: {@link DrinkService#createDrink(DrinkCreationRequest)}
     */
    @Test
    void testCreateDrink6() throws Exception {
        // Arrange
        when(drinkRepository.existsDrinkByName(Mockito.<String>any())).thenReturn(false);
        Optional<DrinkCategory> emptyResult = Optional.empty();
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);

        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");

        Drink drink = new Drink();
        drink.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink.setDescription("The characteristics of someone or something");
        drink.setDrinkCategory(drinkCategory);
        drink.setId(UUID.randomUUID());
        drink.setImage("Image");
        drink.setMenus(new ArrayList<>());
        drink.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink.setName("Name");
        drink.setPrice(10.0d);
        drink.setStatus(true);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        DrinkResponse expectedResponse = new DrinkResponse();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkResponse>>any()))
                .thenReturn(expectedResponse);
        // Act and Assert
        assertThrows(NotFoundException.class, () -> drinkService.createDrink(new DrinkCreationRequest()));
        verify(drinkRepository).existsDrinkByName(isNull());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
        verify(drinkCategoryRepository).findById(isNull());
    }

    /**
     * Method under test:
     * {@link DrinkService#updateDrink(UUID, DrinkUpdationRequest)}
     */
    @Test
    void testUpdateDrink() throws Exception {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");

        Drink drink = new Drink();
        drink.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink.setDescription("The characteristics of someone or something");
        drink.setDrinkCategory(drinkCategory);
        drink.setId(UUID.randomUUID());
        drink.setImage("Image");
        drink.setMenus(new ArrayList<>());
        drink.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink.setName("Name");
        drink.setPrice(10.0d);
        drink.setStatus(true);
        Optional<Drink> ofResult = Optional.of(drink);

        DrinkCategory drinkCategory2 = new DrinkCategory();
        drinkCategory2.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setDescription("The characteristics of someone or something");
        drinkCategory2.setDrinks(new ArrayList<>());
        drinkCategory2.setId(UUID.randomUUID());
        drinkCategory2.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setName("Name");

        Drink drink2 = new Drink();
        drink2.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink2.setDescription("The characteristics of someone or something");
        drink2.setDrinkCategory(drinkCategory2);
        drink2.setId(UUID.randomUUID());
        drink2.setImage("Image");
        drink2.setMenus(new ArrayList<>());
        drink2.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink2.setName("Name");
        drink2.setPrice(10.0d);
        drink2.setStatus(true);
        when(drinkRepository.save(Mockito.<Drink>any())).thenReturn(drink2);
        when(drinkRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        DrinkCategory drinkCategory3 = new DrinkCategory();
        drinkCategory3.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory3.setDescription("The characteristics of someone or something");
        drinkCategory3.setDrinks(new ArrayList<>());
        drinkCategory3.setId(UUID.randomUUID());
        drinkCategory3.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory3.setName("Name");
        Optional<DrinkCategory> ofResult2 = Optional.of(drinkCategory3);
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult2);
        DrinkResponse drinkResponse = new DrinkResponse();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkResponse>>any())).thenReturn(drinkResponse);
        doNothing().when(modelMapper).map(Mockito.<Object>any(), Mockito.<Object>any());
        when(cloudinaryService.updateFiles(Mockito.<List<String>>any(), Mockito.<List<MultipartFile>>any()))
                .thenReturn(new ArrayList<>());
        UUID id = UUID.randomUUID();

        // Act
        DrinkResponse actualUpdateDrinkResult = drinkService.updateDrink(id, new DrinkUpdationRequest());

        // Assert
        verify(cloudinaryService).updateFiles(isNull(), isNull());
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(modelMapper).map(isA(Object.class), isA(Object.class));
        verify(drinkCategoryRepository).findById(isNull());
        verify(drinkRepository).findById(isA(UUID.class));
        verify(drinkRepository).save(isA(Drink.class));
        assertSame(drinkResponse, actualUpdateDrinkResult);
    }

    /**
     * Method under test:
     * {@link DrinkService#updateDrink(UUID, DrinkUpdationRequest)}
     */
    @Test
    void testUpdateDrink2() throws Exception {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");

        Drink drink = new Drink();
        drink.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink.setDescription("The characteristics of someone or something");
        drink.setDrinkCategory(drinkCategory);
        drink.setId(UUID.randomUUID());
        drink.setImage("Image");
        drink.setMenus(new ArrayList<>());
        drink.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink.setName("Name");
        drink.setPrice(10.0d);
        drink.setStatus(true);
        Optional<Drink> ofResult = Optional.of(drink);
        when(drinkRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        DrinkCategory drinkCategory2 = new DrinkCategory();
        drinkCategory2.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setDescription("The characteristics of someone or something");
        drinkCategory2.setDrinks(new ArrayList<>());
        drinkCategory2.setId(UUID.randomUUID());
        drinkCategory2.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setName("Name");
        Optional<DrinkCategory> ofResult2 = Optional.of(drinkCategory2);
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult2);

        DrinkResponse expectedResponse = new DrinkResponse();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkResponse>>any()))
            .thenReturn(expectedResponse);

        // Act and Assert
        assertThrows(Exception.class, () -> drinkService.updateDrink(UUID.randomUUID(), null));
        verify(drinkRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test:
     * {@link DrinkService#updateDrink(UUID, DrinkUpdationRequest)}
     */
    @Test
    void testUpdateDrink3() throws Exception {
        // Arrange
        Optional<Drink> emptyResult = Optional.empty();
        when(drinkRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);

        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");
        Optional<DrinkCategory> ofResult = Optional.of(drinkCategory);
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        DrinkResponse expectedResponse = new DrinkResponse();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkResponse>>any()))
                .thenReturn(expectedResponse);
        UUID id = UUID.randomUUID();

        // Act and Assert
        assertThrows(NotFoundException.class, () -> drinkService.updateDrink(id, new DrinkUpdationRequest()));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
        verify(drinkRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test:
     * {@link DrinkService#updateDrink(UUID, DrinkUpdationRequest)}
     */
    @Test
    void testUpdateDrink4() throws Exception {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");

        Drink drink = new Drink();
        drink.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink.setDescription("The characteristics of someone or something");
        drink.setDrinkCategory(drinkCategory);
        drink.setId(UUID.randomUUID());
        drink.setImage("Image");
        drink.setMenus(new ArrayList<>());
        drink.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink.setName("Name");
        drink.setPrice(10.0d);
        drink.setStatus(true);
        Optional<Drink> ofResult = Optional.of(drink);
        when(drinkRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        Optional<DrinkCategory> emptyResult = Optional.empty();
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkResponse>>any())).thenReturn(new DrinkResponse());
        UUID id = UUID.randomUUID();

        // Act and Assert
        assertThrows(NotFoundException.class, () -> drinkService.updateDrink(id, new DrinkUpdationRequest()));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
        verify(drinkCategoryRepository).findById(isNull());
        verify(drinkRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test:
     * {@link DrinkService#updateDrink(UUID, DrinkUpdationRequest)}
     */
    @Test
    void testUpdateDrink5() throws Exception {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");

        Drink drink = new Drink();
        drink.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink.setDescription("The characteristics of someone or something");
        drink.setDrinkCategory(drinkCategory);
        drink.setId(UUID.randomUUID());
        drink.setImage("Image");
        drink.setMenus(new ArrayList<>());
        drink.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink.setName("Name");
        drink.setPrice(10.0d);
        drink.setStatus(true);
        Optional<Drink> ofResult = Optional.of(drink);
        when(drinkRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        DrinkCategory drinkCategory2 = new DrinkCategory();
        drinkCategory2.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setDescription("The characteristics of someone or something");
        drinkCategory2.setDrinks(new ArrayList<>());
        drinkCategory2.setId(UUID.randomUUID());
        drinkCategory2.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setName("Name");
        Optional<DrinkCategory> ofResult2 = Optional.of(drinkCategory2);
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult2);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkResponse>>any())).thenReturn(new DrinkResponse());

        // Act and Assert
        assertThrows(Exception.class, () -> drinkService.updateDrink(UUID.randomUUID(), null));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
        verify(drinkRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test: {@link DrinkService#deleteDrink(UUID)}
     */
    @Test
    void testDeleteDrink() {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");

        Drink drink = new Drink();
        drink.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink.setDescription("The characteristics of someone or something");
        drink.setDrinkCategory(drinkCategory);
        drink.setId(UUID.randomUUID());
        drink.setImage("Image");
        drink.setMenus(new ArrayList<>());
        drink.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink.setName("Name");
        drink.setPrice(10.0d);
        drink.setStatus(true);
        Optional<Drink> ofResult = Optional.of(drink);
        doNothing().when(drinkRepository).delete(Mockito.<Drink>any());
        when(drinkRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        // Act
        boolean actualDeleteDrinkResult = drinkService.deleteDrink(UUID.randomUUID());

        // Assert
        verify(drinkRepository).delete(isA(Drink.class));
        verify(drinkRepository).findById(isA(UUID.class));
        assertTrue(actualDeleteDrinkResult);
    }

    /**
     * Method under test: {@link DrinkService#deleteDrink(UUID)}
     */
    @Test
    void testDeleteDrink2() {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");

        Drink drink = new Drink();
        drink.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink.setDescription("The characteristics of someone or something");
        drink.setDrinkCategory(drinkCategory);
        drink.setId(UUID.randomUUID());
        drink.setImage("Image");
        drink.setMenus(new ArrayList<>());
        drink.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink.setName("Name");
        drink.setPrice(10.0d);
        drink.setStatus(true);
        Optional<Drink> ofResult = Optional.of(drink);
        doThrow(new ExistDataException("An error occurred")).when(drinkRepository).delete(Mockito.<Drink>any());
        when(drinkRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ExistDataException.class, () -> drinkService.deleteDrink(UUID.randomUUID()));
        verify(drinkRepository).delete(isA(Drink.class));
        verify(drinkRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test: {@link DrinkService#deleteDrink(UUID)}
     */
    @Test
    void testDeleteDrink3() {
        // Arrange
        Optional<Drink> emptyResult = Optional.empty();
        when(drinkRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> drinkService.deleteDrink(UUID.randomUUID()));
        verify(drinkRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test:
     * {@link DrinkService#getAllDrinks(String, String, String, Integer, Integer)}
     */
    @Test
    void testGetAllDrinks() {
        // Arrange
        when(genericSpecification.getFilters(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Function<Root<Drink>, Order>>any())).thenThrow(new ExistDataException("An error occurred"));

        // Act and Assert
        assertThrows(ExistDataException.class,
                () -> drinkService.getAllDrinks("foo", "Include Properties", "Attribute", 1, 3));
        verify(genericSpecification).getFilters(eq("foo"), eq("Include Properties"), eq("Attribute"), isNull());
    }

    /**
     * Method under test:
     * {@link DrinkService#getAllDrinks(String, String, String, Integer, Integer)}
     */
    @Test
    void testGetAllDrinks2() {
        // Arrange
        when(drinkRepository.findAll(Mockito.<Specification<Drink>>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        when(genericSpecification.getFilters(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Function<Root<Drink>, Order>>any())).thenReturn(null);

        // Act
        Page<DrinkResponse> actualAllDrinks = drinkService.getAllDrinks("foo", "Include Properties", "Attribute", 1, 3);

        // Assert
        verify(genericSpecification).getFilters(eq("foo"), eq("Include Properties"), eq("Attribute"), isNull());
        verify(drinkRepository).findAll((Specification<Drink>) isNull(), isA(Pageable.class));
        assertTrue(actualAllDrinks.toList().isEmpty());
    }

    /**
     * Method under test:
     * {@link DrinkService#getAllDrinks(String, String, String, Integer, Integer)}
     */
    @Test
    void testGetAllDrinks3() {
        // Arrange
        when(genericSpecification.getFilters(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Function<Root<Drink>, Order>>any())).thenThrow(new ExistDataException("An error occurred"));

        // Act and Assert
        assertThrows(ExistDataException.class,
                () -> drinkService.getAllDrinks("foo", "Include Properties", "Attribute", 0, 3));
        verify(genericSpecification).getFilters(eq("foo"), eq("Include Properties"), eq("Attribute"), isNull());
    }

    /**
     * Method under test:
     * {@link DrinkService#getAllDrinks(String, String, String, Integer, Integer)}
     */
    @Test
    void testGetAllDrinks4() {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("Description");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("com.ducnt.chillshaker.model.DrinkCategory");

        Drink drink = new Drink();
        drink.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink.setDescription("Description");
        drink.setDrinkCategory(drinkCategory);
        drink.setId(UUID.randomUUID());
        drink.setImage("com.ducnt.chillshaker.model.Drink");
        drink.setMenus(new ArrayList<>());
        drink.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink.setName("com.ducnt.chillshaker.model.Drink");
        drink.setPrice(0.5d);
        drink.setStatus(true);

        ArrayList<Drink> content = new ArrayList<>();
        content.add(drink);
        PageImpl<Drink> pageImpl = new PageImpl<>(content);
        when(drinkRepository.findAll(Mockito.<Specification<Drink>>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkResponse>>any())).thenReturn(new DrinkResponse());
        when(genericSpecification.getFilters(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Function<Root<Drink>, Order>>any())).thenReturn(null);

        // Act
        Page<DrinkResponse> actualAllDrinks = drinkService.getAllDrinks("foo", "Include Properties", "Attribute", 1, 3);

        // Assert
        verify(genericSpecification).getFilters(eq("foo"), eq("Include Properties"), eq("Attribute"), isNull());
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(drinkRepository).findAll((Specification<Drink>) isNull(), isA(Pageable.class));
        assertEquals(1, actualAllDrinks.toList().size());
    }

    /**
     * Method under test:
     * {@link DrinkService#getAllDrinks(String, String, String, Integer, Integer)}
     */
    @Test
    void testGetAllDrinks5() {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("Description");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("com.ducnt.chillshaker.model.DrinkCategory");

        Drink drink = new Drink();
        drink.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink.setDescription("Description");
        drink.setDrinkCategory(drinkCategory);
        drink.setId(UUID.randomUUID());
        drink.setImage("com.ducnt.chillshaker.model.Drink");
        drink.setMenus(new ArrayList<>());
        drink.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink.setName("com.ducnt.chillshaker.model.Drink");
        drink.setPrice(0.5d);
        drink.setStatus(true);

        DrinkCategory drinkCategory2 = new DrinkCategory();
        drinkCategory2.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setDescription("com.ducnt.chillshaker.model.DrinkCategory");
        drinkCategory2.setDrinks(new ArrayList<>());
        drinkCategory2.setId(UUID.randomUUID());
        drinkCategory2.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setName("42");

        Drink drink2 = new Drink();
        drink2.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink2.setDescription("com.ducnt.chillshaker.model.Drink");
        drink2.setDrinkCategory(drinkCategory2);
        drink2.setId(UUID.randomUUID());
        drink2.setImage("42");
        drink2.setMenus(new ArrayList<>());
        drink2.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink2.setName("42");
        drink2.setPrice(-0.5d);
        drink2.setStatus(false);

        ArrayList<Drink> content = new ArrayList<>();
        content.add(drink2);
        content.add(drink);
        PageImpl<Drink> pageImpl = new PageImpl<>(content);
        when(drinkRepository.findAll(Mockito.<Specification<Drink>>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkResponse>>any())).thenReturn(new DrinkResponse());
        when(genericSpecification.getFilters(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Function<Root<Drink>, Order>>any())).thenReturn(null);

        // Act
        Page<DrinkResponse> actualAllDrinks = drinkService.getAllDrinks("foo", "Include Properties", "Attribute", 1, 3);

        // Assert
        verify(genericSpecification).getFilters(eq("foo"), eq("Include Properties"), eq("Attribute"), isNull());
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), isA(Class.class));
        verify(drinkRepository).findAll((Specification<Drink>) isNull(), isA(Pageable.class));
        assertEquals(2, actualAllDrinks.toList().size());
    }

    /**
     * Method under test: {@link DrinkService#getDrinkById(UUID)}
     */
    @Test
    void testGetDrinkById() {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");

        Drink drink = new Drink();
        drink.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink.setDescription("The characteristics of someone or something");
        drink.setDrinkCategory(drinkCategory);
        drink.setId(UUID.randomUUID());
        drink.setImage("Image");
        drink.setMenus(new ArrayList<>());
        drink.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink.setName("Name");
        drink.setPrice(10.0d);
        drink.setStatus(true);
        Optional<Drink> ofResult = Optional.of(drink);
        when(drinkRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        DrinkResponse drinkResponse = new DrinkResponse();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkResponse>>any())).thenReturn(drinkResponse);

        // Act
        DrinkResponse actualDrinkById = drinkService.getDrinkById(UUID.randomUUID());

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(drinkRepository).findById(isA(UUID.class));
        assertSame(drinkResponse, actualDrinkById);
    }

    /**
     * Method under test: {@link DrinkService#getDrinkById(UUID)}
     */
    @Test
    void testGetDrinkById2() {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");

        Drink drink = new Drink();
        drink.setCreatedDate(LocalDate.of(1970, 1, 1));
        drink.setDescription("The characteristics of someone or something");
        drink.setDrinkCategory(drinkCategory);
        drink.setId(UUID.randomUUID());
        drink.setImage("Image");
        drink.setMenus(new ArrayList<>());
        drink.setModifiedDate(LocalDate.of(1970, 1, 1));
        drink.setName("Name");
        drink.setPrice(10.0d);
        drink.setStatus(true);
        Optional<Drink> ofResult = Optional.of(drink);
        when(drinkRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkResponse>>any()))
                .thenThrow(new ExistDataException("An error occurred"));

        // Act and Assert
        assertThrows(ExistDataException.class, () -> drinkService.getDrinkById(UUID.randomUUID()));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(drinkRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test: {@link DrinkService#getDrinkById(UUID)}
     */
    @Test
    void testGetDrinkById3() {
        // Arrange
        Optional<Drink> emptyResult = Optional.empty();
        when(drinkRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkResponse>>any())).thenReturn(new DrinkResponse());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> drinkService.getDrinkById(UUID.randomUUID()));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
        verify(drinkRepository).findById(isA(UUID.class));
    }
}
