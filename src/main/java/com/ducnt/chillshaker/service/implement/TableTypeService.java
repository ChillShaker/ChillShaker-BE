package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.dto.request.tableType.TableTypeCreationRequest;
import com.ducnt.chillshaker.dto.request.tableType.TableTypeUpdateRequest;
import com.ducnt.chillshaker.dto.response.tableType.TableTypeResponse;
import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import com.ducnt.chillshaker.exception.ExistDataException;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.TableType;
import com.ducnt.chillshaker.repository.GenericSpecification;
import com.ducnt.chillshaker.repository.TableTypeRepository;
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

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TableTypeService {
    TableTypeRepository tableTypeRepository;
    ModelMapper modelMapper;
    CloudinaryService cloudinaryService;
    GenericSpecification<TableType> genericSpecification;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public TableTypeResponse createTableType(TableTypeCreationRequest request) {
        try {
            if(tableTypeRepository.existsByName(request.getName()))
                throw new ExistDataException("BarTable Type has exist name");
            TableType tableType = modelMapper.map(request, TableType.class);

            List<String> tableTypeImageUrls = cloudinaryService.uploadFiles(request.getFiles());
            tableType.setImage(String.join(", ", tableTypeImageUrls));
            tableTypeRepository.save(tableType);
            return modelMapper.map(tableType, TableTypeResponse.class);
        } catch (ExistDataException ex) {
            throw new ExistDataException(ex.getMessage());
        } catch (IOException | RuntimeException ex) {
            throw new CustomException(ErrorResponse.IMAGE_FILE_INVALID);
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public TableTypeResponse updateTableType(UUID id, TableTypeUpdateRequest request) {
        try {
            TableType tableType = tableTypeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("BarTable Type is not found"));
            modelMapper.map(request, tableType);

            List<String> updatedImageUrls = cloudinaryService.updateFiles(request.getOldFileUrls(), request.getNewFiles());
            tableType.setImage(String.join(", ", updatedImageUrls));

            tableTypeRepository.save(tableType);

            return modelMapper.map(tableType, TableTypeResponse.class);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (IOException ex) {
            throw new CustomException(ErrorResponse.IMAGE_FILE_INVALID);
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public boolean deleteTableType(UUID id) {
       try {
           TableType tableType = tableTypeRepository.findById(id)
                   .orElseThrow(() -> new NotFoundException("BarTable Type is not found"));
           tableTypeRepository.delete(tableType);
           return true;
       } catch (NotFoundException ex) {
           throw new NotFoundException(ex.getMessage());
       } catch (Exception ex) {
           throw new CustomException(ErrorResponse.INTERNAL_SERVER);
       }
    }

    public Page<TableTypeResponse> getAllTableTypes(
            String q,
            String includeProperties,
            String attribute,
            String sort,
            Integer pageIndex,
            Integer pageSize
    ) {
        try {
            PageRequest pageRequest = PageRequest.of(
                    pageIndex > 0 ? pageIndex - 1 : 0,
                    pageSize,
                    sort.equals("desc") ? Sort.by(Sort.Direction.DESC, attribute) :
                            Sort.by(Sort.Direction.ASC, attribute)
            );

            long totalOfElements = tableTypeRepository.count();

            var filters = genericSpecification.getFilters(q, includeProperties, attribute);
            Page<TableType> tableTypes = tableTypeRepository.findAll(filters, pageRequest);

            List<TableTypeResponse> tableTypeResponses = tableTypes.getContent().stream()
                    .map((element) -> modelMapper.map(element, TableTypeResponse.class))
                    .toList();
            return new PageImpl<>(tableTypeResponses, tableTypes.getPageable(), totalOfElements);
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    public TableTypeResponse getTableTypeById(UUID id) {
        try {
            TableType tableType = tableTypeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("BarTable Type is not found"));
            return modelMapper.map(tableType, TableTypeResponse.class);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }
}
