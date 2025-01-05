package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.dto.request.menu.MenuCreationRequest;
import com.ducnt.chillshaker.dto.request.menu.MenuUpdateRequest;
import com.ducnt.chillshaker.dto.response.menu.MenuResponse;
import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.Menu;
import com.ducnt.chillshaker.repository.GenericSpecification;
import com.ducnt.chillshaker.repository.MenuRepository;
import com.ducnt.chillshaker.service.thirdparty.CloudinaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private GenericSpecification<Menu> genericSpecification;

    @InjectMocks
    private MenuService menuService;

    private UUID menuId;
    private Menu menu;
    private MenuResponse menuResponse;
    private List<MultipartFile> mockFiles;

    @BeforeEach
    void setUp() {
        menuId = UUID.randomUUID();
        menu = new Menu();
        menu.setId(menuId);
        menu.setName("Test Menu");
        menu.setImages("image1.jpg, image2.jpg");
        menu.setStatus(true);

        menuResponse = new MenuResponse();
        menuResponse.setId(menuId);
        menuResponse.setName("Test Menu");
        menuResponse.setImages("image1.jpg, image2.jpg");

        mockFiles = Arrays.asList(
            new MockMultipartFile("file1", "test1.jpg", "image/jpeg", "test1".getBytes()),
            new MockMultipartFile("file2", "test2.jpg", "image/jpeg", "test2".getBytes())
        );
    }

    @Test
    void createMenu_Success() throws IOException {
        // Arrange
        MenuCreationRequest request = new MenuCreationRequest();
        request.setFiles(mockFiles);
        
        when(modelMapper.map(request, Menu.class)).thenReturn(menu);
        when(cloudinaryService.uploadFiles(mockFiles)).thenReturn(Arrays.asList("url1", "url2"));
        when(menuRepository.save(any(Menu.class))).thenReturn(menu);
        when(modelMapper.map(menu, MenuResponse.class)).thenReturn(menuResponse);

        // Act
        MenuResponse result = menuService.createMenu(request);

        // Assert
        assertNotNull(result);
        assertEquals(menuResponse.getId(), result.getId());
        verify(menuRepository).save(any(Menu.class));
    }

    @Test
    void createMenu_ThrowsCustomException() {
        // Arrange
        MenuCreationRequest request = new MenuCreationRequest();
        when(modelMapper.map(request, Menu.class)).thenThrow(new RuntimeException());

        // Act & Assert
        assertThrows(CustomException.class, () -> menuService.createMenu(request));
    }

    @Test
    void updateMenu_Success() throws IOException {
        // Arrange
        MenuUpdateRequest request = new MenuUpdateRequest();
        request.setNewfiles(mockFiles);
        request.setOldUrls(Arrays.asList("old1.jpg", "old2.jpg"));

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));
        doNothing().when(modelMapper).map(menu, request);
        
        when(cloudinaryService.updateFiles(request.getOldUrls(), request.getNewfiles()))
            .thenReturn(Arrays.asList("new1.jpg", "new2.jpg"));
        when(modelMapper.map(menu, MenuResponse.class)).thenReturn(menuResponse);

        // Act
        MenuResponse result = menuService.updateMenu(menuId, request);

        // Assert
        assertNotNull(result);
        assertEquals(menuResponse.getId(), result.getId());
        
        // Verify các method được gọi
        verify(modelMapper).map(menu, request);
        verify(modelMapper).map(menu, MenuResponse.class);
        verify(cloudinaryService).updateFiles(request.getOldUrls(), request.getNewfiles());
    }

    @Test
    void updateMenu_NotFound() {
        // Arrange
        MenuUpdateRequest request = new MenuUpdateRequest();
        when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> menuService.updateMenu(menuId, request));
    }

    @Test
    void deleteMenu_Success() {
        // Arrange
        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));

        // Act
        boolean result = menuService.deleteMenu(menuId);

        // Assert
        assertTrue(result);
        assertFalse(menu.isStatus());
    }

    @Test
    void deleteMenu_NotFound() {
        // Arrange
        when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> menuService.deleteMenu(menuId));
    }

    @Test
    void getAllMenu_Success() {
        // Arrange
        int pageIndex = 1;
        int pageSize = 10;
        String sort = "asc";
        String attribute = "name";
        String query = "test";
        String includeProperties = "name,status";
        
        Specification<Menu> mockSpecification = mock(Specification.class);
        Page<Menu> menuPage = new PageImpl<>(List.of(menu));
        
        // Mock cho genericSpecification.getFilters
        when(genericSpecification.getFilters(query, includeProperties, attribute))
            .thenReturn(mockSpecification);
            
        when(menuRepository.count()).thenReturn(1L);
        when(menuRepository.findAll(mockSpecification, PageRequest.of(
                0, pageSize, 
                Sort.by(Sort.Direction.ASC, attribute)))
        ).thenReturn(menuPage);
        when(modelMapper.map(any(Menu.class), eq(MenuResponse.class))).thenReturn(menuResponse);

        // Act
        Page<MenuResponse> result = menuService.getAllMenu(
            query, 
            includeProperties, 
            attribute, 
            pageIndex, 
            pageSize, 
            sort
        );

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(menuResponse, result.getContent().get(0));
        
        // Verify rằng getFilters được gọi với đúng tham số
        verify(genericSpecification).getFilters(query, includeProperties, attribute);
    }

    @Test
    void getMenuById_Success() {
        // Arrange
        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));
        when(modelMapper.map(menu, MenuResponse.class)).thenReturn(menuResponse);

        // Act
        MenuResponse result = menuService.getMenuById(menuId);

        // Assert
        assertNotNull(result);
        assertEquals(menuResponse.getId(), result.getId());
    }

    @Test
    void getMenuById_NotFound() {
        // Arrange
        when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> menuService.getMenuById(menuId));
    }
}
