package com.ducnt.chillshaker.service.implement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import com.ducnt.chillshaker.dto.request.drinkCategory.DrinkCategoryCreationRequest;
import com.ducnt.chillshaker.dto.request.drinkCategory.DrinkCategoryUpdationRequest;
import com.ducnt.chillshaker.dto.response.drinkCategory.DrinkCategoryResponse;
import com.ducnt.chillshaker.exception.ExistDataException;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.DrinkCategory;
import com.ducnt.chillshaker.repository.DrinkCategoryRepository;
import com.ducnt.chillshaker.repository.GenericSpecification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

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

@ContextConfiguration(classes = {DrinkCategoryService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DrinkCategoryServiceDiffblueTest {
    @MockBean
    private DrinkCategoryRepository drinkCategoryRepository;

    @Autowired
    private DrinkCategoryService drinkCategoryService;

    @MockBean
    private GenericSpecification<DrinkCategory> genericSpecification;

    @MockBean
    private ModelMapper modelMapper;

    /**
     * Method under test:
     * {@link DrinkCategoryService#createDrinkCategory(DrinkCategoryCreationRequest)}
     */
    @Test
    void testCreateDrinkCategory() {
        // Arrange
        when(drinkCategoryRepository.existsByName(Mockito.<String>any())).thenReturn(true);

        // Act and Assert
        assertThrows(ExistDataException.class, () -> drinkCategoryService
                .createDrinkCategory(new DrinkCategoryCreationRequest("Name", "The characteristics of someone or something")));
        verify(drinkCategoryRepository).existsByName(eq("Name"));
    }

    /**
     * Method under test:
     * {@link DrinkCategoryService#createDrinkCategory(DrinkCategoryCreationRequest)}
     */
    @Test
    void testCreateDrinkCategory2() {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");
        when(drinkCategoryRepository.existsByName(Mockito.<String>any())).thenReturn(false);
        when(drinkCategoryRepository.save(Mockito.<DrinkCategory>any())).thenReturn(drinkCategory);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(null);

        // Act
        DrinkCategoryResponse actualCreateDrinkCategoryResult = drinkCategoryService
                .createDrinkCategory(new DrinkCategoryCreationRequest("Name", "The characteristics of someone or something"));

        // Assert
        verify(drinkCategoryRepository).existsByName(eq("Name"));
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
        verify(drinkCategoryRepository).save(isNull());
        assertNull(actualCreateDrinkCategoryResult);
    }

    /**
     * Method under test:
     * {@link DrinkCategoryService#createDrinkCategory(DrinkCategoryCreationRequest)}
     */
    @Test
    void testCreateDrinkCategory3() {
        // Arrange
        when(drinkCategoryRepository.existsByName(Mockito.<String>any())).thenReturn(false);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
                .thenThrow(new ExistDataException("An error occurred"));

        // Act and Assert
        assertThrows(ExistDataException.class, () -> drinkCategoryService
                .createDrinkCategory(new DrinkCategoryCreationRequest("Name", "The characteristics of someone or something")));
        verify(drinkCategoryRepository).existsByName(eq("Name"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }

    /**
     * Method under test:
     * {@link DrinkCategoryService#updateDrinkCategory(UUID, DrinkCategoryUpdationRequest)}
     */
    @Test
    void testUpdateDrinkCategory() {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");
        Optional<DrinkCategory> ofResult = Optional.of(drinkCategory);

        DrinkCategory drinkCategory2 = new DrinkCategory();
        drinkCategory2.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setDescription("The characteristics of someone or something");
        drinkCategory2.setDrinks(new ArrayList<>());
        drinkCategory2.setId(UUID.randomUUID());
        drinkCategory2.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setName("Name");
        when(drinkCategoryRepository.save(Mockito.<DrinkCategory>any())).thenReturn(drinkCategory2);
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        DrinkCategoryResponse drinkCategoryResponse = new DrinkCategoryResponse();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkCategoryResponse>>any()))
                .thenReturn(drinkCategoryResponse);
        doNothing().when(modelMapper).map(Mockito.<Object>any(), Mockito.<Object>any());
        UUID id = UUID.randomUUID();

        // Act
        DrinkCategoryResponse actualUpdateDrinkCategoryResult = drinkCategoryService.updateDrinkCategory(id,
                new DrinkCategoryUpdationRequest());

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(modelMapper).map(isA(Object.class), isA(Object.class));
        verify(drinkCategoryRepository).findById(isA(UUID.class));
        verify(drinkCategoryRepository).save(isA(DrinkCategory.class));
        assertSame(drinkCategoryResponse, actualUpdateDrinkCategoryResult);
    }

    /**
     * Method under test:
     * {@link DrinkCategoryService#updateDrinkCategory(UUID, DrinkCategoryUpdationRequest)}
     */
    @Test
    void testUpdateDrinkCategory2() {
        // Arrange
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
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkCategoryResponse>>any()))
                .thenThrow(new NotFoundException("An error occurred"));
        doThrow(new NotFoundException("An error occurred")).when(modelMapper)
                .map(Mockito.<Object>any(), Mockito.<Object>any());
        UUID id = UUID.randomUUID();

        // Act and Assert
        assertThrows(NotFoundException.class,
                () -> drinkCategoryService.updateDrinkCategory(id, new DrinkCategoryUpdationRequest()));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
        verify(modelMapper).map(isA(Object.class), isA(Object.class));
        verify(drinkCategoryRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test:
     * {@link DrinkCategoryService#updateDrinkCategory(UUID, DrinkCategoryUpdationRequest)}
     */
    @Test
    void testUpdateDrinkCategory3() {
        // Arrange
        Optional<DrinkCategory> emptyResult = Optional.empty();
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkCategoryResponse>>any()))
                .thenThrow(new NotFoundException("An error occurred"));
        UUID id = UUID.randomUUID();

        // Act and Assert
        assertThrows(NotFoundException.class,
                () -> drinkCategoryService.updateDrinkCategory(id, new DrinkCategoryUpdationRequest()));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
        verify(drinkCategoryRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test: {@link DrinkCategoryService#deleteDrinkCategory(UUID)}
     */
    @Test
    void testDeleteDrinkCategory() {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");
        Optional<DrinkCategory> ofResult = Optional.of(drinkCategory);
        doNothing().when(drinkCategoryRepository).delete(Mockito.<DrinkCategory>any());
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        // Act
        boolean actualDeleteDrinkCategoryResult = drinkCategoryService.deleteDrinkCategory(UUID.randomUUID());

        // Assert
        verify(drinkCategoryRepository).delete(isA(DrinkCategory.class));
        verify(drinkCategoryRepository).findById(isA(UUID.class));
        assertTrue(actualDeleteDrinkCategoryResult);
    }

    /**
     * Method under test: {@link DrinkCategoryService#deleteDrinkCategory(UUID)}
     */
    @Test
    void testDeleteDrinkCategory2() {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");
        Optional<DrinkCategory> ofResult = Optional.of(drinkCategory);
        doThrow(new ExistDataException("An error occurred")).when(drinkCategoryRepository)
                .delete(Mockito.<DrinkCategory>any());
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ExistDataException.class, () -> drinkCategoryService.deleteDrinkCategory(UUID.randomUUID()));
        verify(drinkCategoryRepository).delete(isA(DrinkCategory.class));
        verify(drinkCategoryRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test: {@link DrinkCategoryService#deleteDrinkCategory(UUID)}
     */
    @Test
    void testDeleteDrinkCategory3() {
        // Arrange
        Optional<DrinkCategory> emptyResult = Optional.empty();
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> drinkCategoryService.deleteDrinkCategory(UUID.randomUUID()));
        verify(drinkCategoryRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test:
     * {@link DrinkCategoryService#getAllDrinkCategory(String, String, String, Integer, Integer)}
     */
    @Test
    void testGetAllDrinkCategory() {
        // Arrange
        when(genericSpecification.getFilters(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenThrow(new ExistDataException("An error occurred"));

        // Act and Assert
        assertThrows(ExistDataException.class,
                () -> drinkCategoryService.getAllDrinkCategory("foo", "Include Properties", "Attribute", 1, 3));
        verify(genericSpecification).getFilters(eq("foo"), eq("Include Properties"), eq("Attribute"), eq(""));
    }

    /**
     * Method under test:
     * {@link DrinkCategoryService#getAllDrinkCategory(String, String, String, Integer, Integer)}
     */
    @Test
    void testGetAllDrinkCategory2() {
        // Arrange
        when(drinkCategoryRepository.findAll(Mockito.<Specification<DrinkCategory>>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        when(genericSpecification.getFilters(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(null);

        // Act
        Page<DrinkCategoryResponse> actualAllDrinkCategory = drinkCategoryService.getAllDrinkCategory("foo",
                "Include Properties", "Attribute", 1, 3);

        // Assert
        verify(genericSpecification).getFilters(eq("foo"), eq("Include Properties"), eq("Attribute"), eq(""));
        verify(drinkCategoryRepository).findAll((Specification<DrinkCategory>) isNull(), isA(Pageable.class));
        assertTrue(actualAllDrinkCategory.toList().isEmpty());
    }

    /**
     * Method under test:
     * {@link DrinkCategoryService#getAllDrinkCategory(String, String, String, Integer, Integer)}
     */
    @Test
    void testGetAllDrinkCategory3() {
        // Arrange
        when(genericSpecification.getFilters(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenThrow(new ExistDataException("An error occurred"));

        // Act and Assert
        assertThrows(ExistDataException.class,
                () -> drinkCategoryService.getAllDrinkCategory("foo", "Include Properties", "Attribute", 0, 3));
        verify(genericSpecification).getFilters(eq("foo"), eq("Include Properties"), eq("Attribute"), eq(""));
    }

    /**
     * Method under test:
     * {@link DrinkCategoryService#getAllDrinkCategory(String, String, String, Integer, Integer)}
     */
    @Test
    void testGetAllDrinkCategory4() {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");

        ArrayList<DrinkCategory> content = new ArrayList<>();
        content.add(drinkCategory);
        PageImpl<DrinkCategory> pageImpl = new PageImpl<>(content);
        when(drinkCategoryRepository.findAll(Mockito.<Specification<DrinkCategory>>any(), Mockito.<Pageable>any()))
                .thenReturn(pageImpl);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkCategoryResponse>>any()))
                .thenReturn(new DrinkCategoryResponse());
        when(genericSpecification.getFilters(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(null);

        // Act
        Page<DrinkCategoryResponse> actualAllDrinkCategory = drinkCategoryService.getAllDrinkCategory("foo",
                "Include Properties", "Attribute", 1, 3);

        // Assert
        verify(genericSpecification).getFilters(eq("foo"), eq("Include Properties"), eq("Attribute"), eq(""));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(drinkCategoryRepository).findAll((Specification<DrinkCategory>) isNull(), isA(Pageable.class));
        assertEquals(1, actualAllDrinkCategory.toList().size());
    }

    /**
     * Method under test:
     * {@link DrinkCategoryService#getAllDrinkCategory(String, String, String, Integer, Integer)}
     */
    @Test
    void testGetAllDrinkCategory5() {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");

        DrinkCategory drinkCategory2 = new DrinkCategory();
        drinkCategory2.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setDescription("Description");
        drinkCategory2.setDrinks(new ArrayList<>());
        drinkCategory2.setId(UUID.randomUUID());
        drinkCategory2.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory2.setName("");

        ArrayList<DrinkCategory> content = new ArrayList<>();
        content.add(drinkCategory2);
        content.add(drinkCategory);
        PageImpl<DrinkCategory> pageImpl = new PageImpl<>(content);
        when(drinkCategoryRepository.findAll(Mockito.<Specification<DrinkCategory>>any(), Mockito.<Pageable>any()))
                .thenReturn(pageImpl);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkCategoryResponse>>any()))
                .thenReturn(new DrinkCategoryResponse());
        when(genericSpecification.getFilters(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(null);

        // Act
        Page<DrinkCategoryResponse> actualAllDrinkCategory = drinkCategoryService.getAllDrinkCategory("foo",
                "Include Properties", "Attribute", 1, 3);

        // Assert
        verify(genericSpecification).getFilters(eq("foo"), eq("Include Properties"), eq("Attribute"), eq(""));
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), isA(Class.class));
        verify(drinkCategoryRepository).findAll((Specification<DrinkCategory>) isNull(), isA(Pageable.class));
        assertEquals(2, actualAllDrinkCategory.toList().size());
    }

    /**
     * Method under test: {@link DrinkCategoryService#getById(UUID)}
     */
    @Test
    void testGetById() {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");
        Optional<DrinkCategory> ofResult = Optional.of(drinkCategory);
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        DrinkCategoryResponse drinkCategoryResponse = new DrinkCategoryResponse();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkCategoryResponse>>any()))
                .thenReturn(drinkCategoryResponse);

        // Act
        DrinkCategoryResponse actualById = drinkCategoryService.getById(UUID.randomUUID());

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(drinkCategoryRepository).findById(isA(UUID.class));
        assertSame(drinkCategoryResponse, actualById);
    }

    /**
     * Method under test: {@link DrinkCategoryService#getById(UUID)}
     */
    @Test
    void testGetById2() {
        // Arrange
        DrinkCategory drinkCategory = new DrinkCategory();
        drinkCategory.setCreatedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setDescription("The characteristics of someone or something");
        drinkCategory.setDrinks(new ArrayList<>());
        drinkCategory.setId(UUID.randomUUID());
        drinkCategory.setModifiedDate(LocalDate.of(1970, 1, 1));
        drinkCategory.setName("Name");
        Optional<DrinkCategory> ofResult = Optional.of(drinkCategory);
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkCategoryResponse>>any()))
                .thenThrow(new ExistDataException("An error occurred"));

        // Act and Assert
        assertThrows(ExistDataException.class, () -> drinkCategoryService.getById(UUID.randomUUID()));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(drinkCategoryRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test: {@link DrinkCategoryService#getById(UUID)}
     */
    @Test
    void testGetById3() {
        // Arrange
        Optional<DrinkCategory> emptyResult = Optional.empty();
        when(drinkCategoryRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<DrinkCategoryResponse>>any()))
                .thenReturn(new DrinkCategoryResponse());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> drinkCategoryService.getById(UUID.randomUUID()));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
        verify(drinkCategoryRepository).findById(isA(UUID.class));
    }
}
