package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.dto.request.tableType.TableTypeCreationRequest;
import com.ducnt.chillshaker.dto.request.tableType.TableTypeUpdationRequest;
import com.ducnt.chillshaker.dto.response.tableType.TableTypeResponse;
import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ExistDataException;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.TableType;
import com.ducnt.chillshaker.repository.GenericSpecification;
import com.ducnt.chillshaker.repository.TableTypeRepository;
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
class TableTypeServiceTest {

    @Mock
    private TableTypeRepository tableTypeRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private GenericSpecification<TableType> genericSpecification;

    @InjectMocks
    private TableTypeService tableTypeService;

    private TableType tableType;
    private TableTypeResponse tableTypeResponse;
    private UUID testId;
    private List<MultipartFile> mockFiles;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        tableType = new TableType();
        tableType.setId(testId);
        tableType.setName("Test Table Type");
        tableType.setImage("image1.jpg, image2.jpg");

        tableTypeResponse = new TableTypeResponse();
        tableTypeResponse.setId(testId);
        tableTypeResponse.setName("Test Table Type");
        tableTypeResponse.setImage("image1.jpg, image2.jpg");

        mockFiles = Arrays.asList(
            new MockMultipartFile("file1", "test1.jpg", "image/jpeg", "test1".getBytes()),
            new MockMultipartFile("file2", "test2.jpg", "image/jpeg", "test2".getBytes())
        );
    }

    @Test
    void createTableType_Success() throws Exception {
        // Arrange
        TableTypeCreationRequest request = new TableTypeCreationRequest();
        request.setName("New Table Type");
        request.setFiles(mockFiles);

        when(tableTypeRepository.existsByName(any())).thenReturn(false);
        when(cloudinaryService.uploadFiles(any())).thenReturn(Arrays.asList("url1", "url2"));
        when(modelMapper.map(any(TableTypeCreationRequest.class), eq(TableType.class))).thenReturn(tableType);
        when(modelMapper.map(any(TableType.class), eq(TableTypeResponse.class))).thenReturn(tableTypeResponse);
        when(tableTypeRepository.save(any())).thenReturn(tableType);

        // Act
        TableTypeResponse result = tableTypeService.createTableType(request);

        // Assert
        assertNotNull(result);
        assertEquals(tableTypeResponse.getId(), result.getId());
        verify(tableTypeRepository).save(any());
    }

    @Test
    void createTableType_ExistingName_ThrowsException() {
        // Arrange
        TableTypeCreationRequest request = new TableTypeCreationRequest();
        request.setName("Existing Name");
        
        when(tableTypeRepository.existsByName(any())).thenReturn(true);

        // Act & Assert
        assertThrows(ExistDataException.class, () -> tableTypeService.createTableType(request));
    }

    @Test
    void updateTableType_Success() throws Exception {
        // Arrange
        TableTypeUpdationRequest request = new TableTypeUpdationRequest();
        request.setName("Updated Name");
        request.setNewFiles(mockFiles);
        request.setOldFileUrls(Arrays.asList("old1.jpg", "old2.jpg"));

        TableType updatedTableType = new TableType();
        updatedTableType.setId(testId);
        updatedTableType.setName("Updated Name");
        updatedTableType.setImage("newUrl1, newUrl2");

        when(tableTypeRepository.findById(testId)).thenReturn(Optional.of(tableType));
        
        doReturn(Arrays.asList("newUrl1", "newUrl2"))
            .when(cloudinaryService)
            .updateFiles(
                eq(Arrays.asList("old1.jpg", "old2.jpg")), 
                eq(mockFiles)
            );
        
        // Sửa cách mock cho modelMapper
        doNothing().when(modelMapper).map(any(TableTypeUpdationRequest.class), any(TableType.class));
        doReturn(tableTypeResponse).when(modelMapper).map(any(TableType.class), eq(TableTypeResponse.class));
        
        when(tableTypeRepository.save(any(TableType.class))).thenReturn(updatedTableType);

        // Act
        TableTypeResponse result = tableTypeService.updateTableType(testId, request);

        // Assert
        assertNotNull(result);
        verify(tableTypeRepository).save(any(TableType.class));
        verify(cloudinaryService).updateFiles(
            eq(Arrays.asList("old1.jpg", "old2.jpg")), 
            eq(mockFiles)
        );
        verify(modelMapper).map(any(TableTypeUpdationRequest.class), any(TableType.class));
        verify(modelMapper).map(any(TableType.class), eq(TableTypeResponse.class));
    }

    @Test
    void updateTableType_NotFound_ThrowsException() {
        // Arrange
        when(tableTypeRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, 
            () -> tableTypeService.updateTableType(testId, new TableTypeUpdationRequest()));
    }

    @Test
    void deleteTableType_Success() {
        // Arrange
        when(tableTypeRepository.findById(testId)).thenReturn(Optional.of(tableType));

        // Act
        boolean result = tableTypeService.deleteTableType(testId);

        // Assert
        assertTrue(result);
        verify(tableTypeRepository).delete(tableType);
    }

    @Test
    void deleteTableType_NotFound_ThrowsException() {
        // Arrange
        when(tableTypeRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> tableTypeService.deleteTableType(testId));
    }

    @Test
    void getAllTableTypes_Success() {
        // Arrange
        List<TableType> tableTypes = Arrays.asList(tableType);
        Page<TableType> tablePage = new PageImpl<>(tableTypes);
        
        when(tableTypeRepository.count()).thenReturn(1L);
        when(genericSpecification.getFilters(any(), any(), any())).thenReturn(null);
        when(tableTypeRepository.findAll((Specification<TableType>) any(), (Pageable) any(PageRequest.class))).thenReturn(tablePage);
        when(modelMapper.map(any(TableType.class), eq(TableTypeResponse.class))).thenReturn(tableTypeResponse);

        // Act
        Page<TableTypeResponse> result = tableTypeService.getAllTableTypes("", "", "name", "asc", 1, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getTableTypeById_Success() {
        // Arrange
        when(tableTypeRepository.findById(testId)).thenReturn(Optional.of(tableType));
        when(modelMapper.map(tableType, TableTypeResponse.class)).thenReturn(tableTypeResponse);

        // Act
        TableTypeResponse result = tableTypeService.getTableTypeById(testId);

        // Assert
        assertNotNull(result);
        assertEquals(testId, result.getId());
    }

    @Test
    void getTableTypeById_NotFound_ThrowsException() {
        // Arrange
        when(tableTypeRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> tableTypeService.getTableTypeById(testId));
    }

    @Test
    void updateTableType_UploadError_ThrowsCustomException() throws Exception {
        // Arrange
        TableTypeUpdationRequest request = new TableTypeUpdationRequest();
        request.setName("Updated Name");
        request.setNewFiles(mockFiles);
        request.setOldFileUrls(Arrays.asList("old1.jpg", "old2.jpg"));

        when(tableTypeRepository.findById(testId)).thenReturn(Optional.of(tableType));
        
        // Sửa cách mock exception
        doThrow(new IOException("Upload failed"))
            .when(cloudinaryService)
            .updateFiles(any(), any());

        // Act & Assert
        assertThrows(CustomException.class, () -> tableTypeService.updateTableType(testId, request));
    }

    @Test
    void createTableType_UploadError_ThrowsCustomException() throws Exception {
        // Arrange
        TableTypeCreationRequest request = new TableTypeCreationRequest();
        request.setName("New Table Type");
        request.setFiles(mockFiles);

        when(tableTypeRepository.existsByName(any())).thenReturn(false);
        
        // Sửa cách mock exception
        doThrow(new IOException("Upload failed"))
            .when(cloudinaryService)
            .uploadFiles(any());

        // Act & Assert
        assertThrows(CustomException.class, () -> tableTypeService.createTableType(request));
    }
}
