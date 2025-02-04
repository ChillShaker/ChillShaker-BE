package com.ducnt.chillshaker.service.implement;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ducnt.chillshaker.dto.request.bar.BarUpdateRequest;
import com.ducnt.chillshaker.dto.response.bar.BarResponse;
import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.Bar;
import com.ducnt.chillshaker.repository.BarRepository;
import com.ducnt.chillshaker.service.thirdparty.CloudinaryService;

import java.io.IOException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {BarService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BarServiceTest {
    @MockBean
    private BarRepository barRepository;

    @Autowired
    private BarService barService;

    @MockBean
    private CloudinaryService cloudinaryService;

    @MockBean
    private ModelMapper modelMapper;

    /**
     * Test {@link BarService#updateBar(UUID, BarUpdateRequest)}.
     * <p>
     * Method under test: {@link BarService#updateBar(UUID, BarUpdateRequest)}
     */
    @Test
    @DisplayName("Test updateBar(UUID, BarUpdateRequest)")
    void testUpdateBar() throws IOException {
        // Arrange
        Bar bar = new Bar();
        bar.setAddress("42 Main St");
        bar.setBarTables(new ArrayList<>());
        bar.setBarTimes(new ArrayList<>());
        bar.setBookings(new ArrayList<>());
        bar.setCreatedDate(LocalDate.of(1970, 1, 1));
        bar.setDescription("The characteristics of someone or something");
        bar.setEmail("jane.doe@example.org");
        bar.setId(UUID.randomUUID());
        bar.setImage("Image");
        bar.setMenus(new ArrayList<>());
        bar.setModifiedDate(LocalDate.of(1970, 1, 1));
        bar.setName("Name");
        bar.setPhone("6625550144");
        bar.setStatus(true);
        Optional<Bar> ofResult = Optional.of(bar);
        when(barRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<BarResponse>>any())).thenReturn(new BarResponse());
        doNothing().when(modelMapper).map(Mockito.<Object>any(), Mockito.<Object>any());
        when(cloudinaryService.updateFiles(Mockito.<List<String>>any(), Mockito.<List<MultipartFile>>any()))
                .thenThrow(new NotFoundException("An error occurred"));
        UUID id = UUID.randomUUID();

        // Act and Assert
        assertThrows(NotFoundException.class, () -> barService.updateBar(id, new BarUpdateRequest()));
        verify(cloudinaryService).updateFiles(isNull(), isA(List.class));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
        verify(modelMapper).map(isA(Object.class), isA(Object.class));
        verify(barRepository).findById(isA(UUID.class));
    }

    /**
     * Test {@link BarService#updateBar(UUID, BarUpdateRequest)}.
     * <ul>
     *   <li>Given {@link Bar#Bar()} Address is {@code 42 Main St}.</li>
     *   <li>When {@code null}.</li>
     *   <li>Then throw {@link CustomException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link BarService#updateBar(UUID, BarUpdateRequest)}
     */
    @Test
    @DisplayName("Test updateBar(UUID, BarUpdateRequest); given Bar() Address is '42 Main St'; when 'null'; then throw CustomException")
    void testUpdateBar_givenBarAddressIs42MainSt_whenNull_thenThrowCustomException() {
        // Arrange
        Bar bar = new Bar();
        bar.setAddress("42 Main St");
        bar.setBarTables(new ArrayList<>());
        bar.setBarTimes(new ArrayList<>());
        bar.setBookings(new ArrayList<>());
        bar.setCreatedDate(LocalDate.of(1970, 1, 1));
        bar.setDescription("The characteristics of someone or something");
        bar.setEmail("jane.doe@example.org");
        bar.setId(UUID.randomUUID());
        bar.setImage("Image");
        bar.setMenus(new ArrayList<>());
        bar.setModifiedDate(LocalDate.of(1970, 1, 1));
        bar.setName("Name");
        bar.setPhone("6625550144");
        bar.setStatus(true);
        Optional<Bar> ofResult = Optional.of(bar);
        when(barRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<BarResponse>>any())).thenReturn(new BarResponse());
        doNothing().when(modelMapper).map(Mockito.<Object>any(), Mockito.<Object>any());

        // Act and Assert
        assertThrows(CustomException.class, () -> barService.updateBar(UUID.randomUUID(), null));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
        verify(modelMapper).map(isNull(), isA(Object.class));
        verify(barRepository).findById(isA(UUID.class));
    }

    /**
     * Test {@link BarService#updateBar(UUID, BarUpdateRequest)}.
     * <ul>
     *   <li>Given {@link BarRepository} {@link CrudRepository#findById(Object)}
     * return empty.</li>
     *   <li>Then throw {@link NotFoundException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link BarService#updateBar(UUID, BarUpdateRequest)}
     */
    @Test
    @DisplayName("Test updateBar(UUID, BarUpdateRequest); given BarRepository findById(Object) return empty; then throw NotFoundException")
    void testUpdateBar_givenBarRepositoryFindByIdReturnEmpty_thenThrowNotFoundException() {
        // Arrange
        Optional<Bar> emptyResult = Optional.empty();
        when(barRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<BarResponse>>any())).thenReturn(new BarResponse());
        UUID id = UUID.randomUUID();

        // Act and Assert
        assertThrows(NotFoundException.class, () -> barService.updateBar(id, new BarUpdateRequest()));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
        verify(barRepository).findById(isA(UUID.class));
    }

    /**
     * Test {@link BarService#updateBar(UUID, BarUpdateRequest)}.
     * <ul>
     *   <li>Given {@link BarRepository} {@link CrudRepository#save(Object)} return
     * {@link Bar#Bar()}.</li>
     *   <li>Then return {@link BarResponse#BarResponse()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link BarService#updateBar(UUID, BarUpdateRequest)}
     */
    @Test
    @DisplayName("Test updateBar(UUID, BarUpdateRequest); given BarRepository save(Object) return Bar(); then return BarResponse()")
    void testUpdateBar_givenBarRepositorySaveReturnBar_thenReturnBarResponse() throws IOException {
        // Arrange
        Bar bar = new Bar();
        bar.setAddress("42 Main St");
        bar.setBarTables(new ArrayList<>());
        bar.setBarTimes(new ArrayList<>());
        bar.setBookings(new ArrayList<>());
        bar.setCreatedDate(LocalDate.of(1970, 1, 1));
        bar.setDescription("The characteristics of someone or something");
        bar.setEmail("jane.doe@example.org");
        bar.setId(UUID.randomUUID());
        bar.setImage("Image");
        bar.setMenus(new ArrayList<>());
        bar.setModifiedDate(LocalDate.of(1970, 1, 1));
        bar.setName("Name");
        bar.setPhone("6625550144");
        bar.setStatus(true);
        Optional<Bar> ofResult = Optional.of(bar);

        Bar bar2 = new Bar();
        bar2.setAddress("42 Main St");
        bar2.setBarTables(new ArrayList<>());
        bar2.setBarTimes(new ArrayList<>());
        bar2.setBookings(new ArrayList<>());
        bar2.setCreatedDate(LocalDate.of(1970, 1, 1));
        bar2.setDescription("The characteristics of someone or something");
        bar2.setEmail("jane.doe@example.org");
        bar2.setId(UUID.randomUUID());
        bar2.setImage("Image");
        bar2.setMenus(new ArrayList<>());
        bar2.setModifiedDate(LocalDate.of(1970, 1, 1));
        bar2.setName("Name");
        bar2.setPhone("6625550144");
        bar2.setStatus(true);
        when(barRepository.save(Mockito.<Bar>any())).thenReturn(bar2);
        when(barRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        BarResponse barResponse = new BarResponse();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<BarResponse>>any())).thenReturn(barResponse);
        doNothing().when(modelMapper).map(Mockito.<Object>any(), Mockito.<Object>any());
        when(cloudinaryService.updateFiles(Mockito.<List<String>>any(), Mockito.<List<MultipartFile>>any()))
                .thenReturn(new ArrayList<>());
        UUID id = UUID.randomUUID();

        // Act
        BarResponse actualUpdateBarResult = barService.updateBar(id, new BarUpdateRequest());

        // Assert
        verify(cloudinaryService).updateFiles(isNull(), isA(List.class));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(modelMapper).map(isA(Object.class), isA(Object.class));
        verify(barRepository).findById(isA(UUID.class));
        verify(barRepository).save(isA(Bar.class));
        assertSame(barResponse, actualUpdateBarResult);
    }

    /**
     * Test {@link BarService#getBarById(UUID)}.
     * <p>
     * Method under test: {@link BarService#getBarById(UUID)}
     */
    @Test
    @DisplayName("Test getBarById(UUID)")
    void testGetBarById() {
        // Arrange
        Bar bar = new Bar();
        bar.setAddress("42 Main St");
        bar.setBarTables(new ArrayList<>());
        bar.setBarTimes(new ArrayList<>());
        bar.setBookings(new ArrayList<>());
        bar.setCreatedDate(LocalDate.of(1970, 1, 1));
        bar.setDescription("The characteristics of someone or something");
        bar.setEmail("jane.doe@example.org");
        bar.setId(UUID.randomUUID());
        bar.setImage("Image");
        bar.setMenus(new ArrayList<>());
        bar.setModifiedDate(LocalDate.of(1970, 1, 1));
        bar.setName("Name");
        bar.setPhone("6625550144");
        bar.setStatus(true);
        Optional<Bar> ofResult = Optional.of(bar);
        when(barRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<BarResponse>>any()))
                .thenThrow(new NotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(NotFoundException.class, () -> barService.getBarById(UUID.randomUUID()));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(barRepository).findById(isA(UUID.class));
    }

    /**
     * Test {@link BarService#getBarById(UUID)}.
     * <ul>
     *   <li>Given {@link Bar#Bar()} Address is {@code 42 Main St}.</li>
     *   <li>Then return {@link BarResponse#BarResponse()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link BarService#getBarById(UUID)}
     */
    @Test
    @DisplayName("Test getBarById(UUID); given Bar() Address is '42 Main St'; then return BarResponse()")
    void testGetBarById_givenBarAddressIs42MainSt_thenReturnBarResponse() {
        // Arrange
        Bar bar = new Bar();
        bar.setAddress("42 Main St");
        bar.setBarTables(new ArrayList<>());
        bar.setBarTimes(new ArrayList<>());
        bar.setBookings(new ArrayList<>());
        bar.setCreatedDate(LocalDate.of(1970, 1, 1));
        bar.setDescription("The characteristics of someone or something");
        bar.setEmail("jane.doe@example.org");
        bar.setId(UUID.randomUUID());
        bar.setImage("Image");
        bar.setMenus(new ArrayList<>());
        bar.setModifiedDate(LocalDate.of(1970, 1, 1));
        bar.setName("Name");
        bar.setPhone("6625550144");
        bar.setStatus(true);
        Optional<Bar> ofResult = Optional.of(bar);
        when(barRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        BarResponse barResponse = new BarResponse();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<BarResponse>>any())).thenReturn(barResponse);

        // Act
        BarResponse actualBarById = barService.getBarById(UUID.randomUUID());

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(barRepository).findById(isA(UUID.class));
        assertSame(barResponse, actualBarById);
    }

    /**
     * Test {@link BarService#getBarById(UUID)}.
     * <ul>
     *   <li>Given {@link BarRepository} {@link CrudRepository#findById(Object)}
     * return empty.</li>
     *   <li>Then throw {@link NotFoundException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link BarService#getBarById(UUID)}
     */
    @Test
    @DisplayName("Test getBarById(UUID); given BarRepository findById(Object) return empty; then throw NotFoundException")
    void testGetBarById_givenBarRepositoryFindByIdReturnEmpty_thenThrowNotFoundException() {
        // Arrange
        Optional<Bar> emptyResult = Optional.empty();
        when(barRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<BarResponse>>any())).thenReturn(new BarResponse());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> barService.getBarById(UUID.randomUUID()));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
        verify(barRepository).findById(isA(UUID.class));
    }
}
