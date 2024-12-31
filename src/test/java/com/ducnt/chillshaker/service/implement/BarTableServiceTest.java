package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.dto.request.barTable.BarTableCreationRequest;
import com.ducnt.chillshaker.dto.request.barTable.BarTableUpdationRequest;
import com.ducnt.chillshaker.dto.response.barTable.BarTableResponse;
import com.ducnt.chillshaker.exception.ExistDataException;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.BarTable;
import com.ducnt.chillshaker.model.TableType;
import com.ducnt.chillshaker.repository.BarTableRepository;
import com.ducnt.chillshaker.repository.GenericSpecification;
import com.ducnt.chillshaker.repository.TableTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BarTableServiceTest {

    @Mock
    private BarTableRepository barTableRepository;

    @Mock
    private TableTypeRepository tableTypeRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private GenericSpecification<BarTable> genericSpecification;

    @InjectMocks
    private BarTableService barTableService;

    private UUID tableId;
    private UUID typeId;
    private BarTable barTable;
    private TableType tableType;
    private BarTableCreationRequest creationRequest;
    private BarTableUpdationRequest updationRequest;
    private BarTableResponse barTableResponse;

    @BeforeEach
    void setUp() {
        tableId = UUID.randomUUID();
        typeId = UUID.randomUUID();
        
        tableType = new TableType();
        tableType.setId(typeId);
        
        barTable = new BarTable();
        barTable.setId(tableId);
        barTable.setName("Table 1");
        barTable.setTableType(tableType);
        
        creationRequest = new BarTableCreationRequest();
        creationRequest.setName("Table 1");
        creationRequest.setTableTypeId(typeId);
        
        updationRequest = new BarTableUpdationRequest();
        updationRequest.setName("Updated Table 1");
        updationRequest.setTableTypeId(typeId);
        
        barTableResponse = new BarTableResponse();
        barTableResponse.setId(tableId);
        barTableResponse.setName("Table 1");
    }

    @Test
    void createBarTable_Success() {
        when(barTableRepository.existsByName(anyString())).thenReturn(false);
        when(tableTypeRepository.findById(any(UUID.class))).thenReturn(Optional.of(tableType));
        when(modelMapper.map(creationRequest, BarTable.class)).thenReturn(barTable);
        when(barTableRepository.save(any(BarTable.class))).thenReturn(barTable);
        when(modelMapper.map(barTable, BarTableResponse.class)).thenReturn(barTableResponse);

        BarTableResponse result = barTableService.createBarTable(creationRequest);

        assertNotNull(result);
        assertEquals(tableId, result.getId());
        verify(barTableRepository).save(any(BarTable.class));
    }

    @Test
    void createBarTable_ExistingName_ThrowsException() {
        when(barTableRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(ExistDataException.class, () -> 
            barTableService.createBarTable(creationRequest)
        );
    }

    @Test
    void createBarTable_TableTypeNotFound_ThrowsException() {
        when(barTableRepository.existsByName(anyString())).thenReturn(false);
        when(tableTypeRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> 
            barTableService.createBarTable(creationRequest)
        );
    }

    @Test
    void updateBarTable_Success() {
        when(barTableRepository.findById(tableId)).thenReturn(Optional.of(barTable));
        when(tableTypeRepository.findById(typeId)).thenReturn(Optional.of(tableType));
        
        doNothing().when(modelMapper).map(any(BarTableUpdationRequest.class), any(BarTable.class));
        
        when(modelMapper.map(any(BarTable.class), eq(BarTableResponse.class))).thenReturn(barTableResponse);
        
        when(barTableRepository.save(any(BarTable.class))).thenReturn(barTable);

        BarTableResponse result = barTableService.updateBarTable(tableId, updationRequest);

        assertNotNull(result);
        verify(barTableRepository).save(any(BarTable.class));
        verify(modelMapper).map(updationRequest, barTable);
    }

    @Test
    void updateBarTable_TableNotFound_ThrowsException() {
        when(barTableRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> 
            barTableService.updateBarTable(tableId, updationRequest)
        );
    }

    @Test
    void deleteBarTable_Success() {
        when(barTableRepository.findById(tableId)).thenReturn(Optional.of(barTable));
        when(barTableRepository.save(any(BarTable.class))).thenReturn(barTable);

        boolean result = barTableService.deleteBarTableById(tableId);

        assertTrue(result);
        verify(barTableRepository).save(any(BarTable.class));
    }

    @Test
    void deleteBarTable_TableNotFound_ThrowsException() {
        when(barTableRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> 
            barTableService.deleteBarTableById(tableId)
        );
    }

    @Test
    void getAllBarTableForCustomer_Success() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<BarTable> barTablePage = new PageImpl<>(Arrays.asList(barTable));
        
        when(barTableRepository.findAllByIsDeletedFalse(any(PageRequest.class))).thenReturn(barTablePage);
        when(modelMapper.map(any(BarTable.class), eq(BarTableResponse.class))).thenReturn(barTableResponse);

        Page<BarTableResponse> result = barTableService.getAllBarTableForCustomer(
            "", "", "id", "asc", 1, 10
        );

        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
    }

    @Test
    void getBarTableById_Success() {
        when(barTableRepository.findById(tableId)).thenReturn(Optional.of(barTable));
        when(modelMapper.map(barTable, BarTableResponse.class)).thenReturn(barTableResponse);

        BarTableResponse result = barTableService.getBarTableById(tableId);

        assertNotNull(result);
        assertEquals(tableId, result.getId());
    }

    @Test
    void getBarTableById_NotFound_ThrowsException() {
        when(barTableRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> 
            barTableService.getBarTableById(tableId)
        );
    }
}
