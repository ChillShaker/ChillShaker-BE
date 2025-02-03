package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.dto.request.barTable.BarTableCreationRequest;
import com.ducnt.chillshaker.dto.request.barTable.BarTableStatusRequest;
import com.ducnt.chillshaker.dto.request.barTable.BarTableUpdateRequest;
import com.ducnt.chillshaker.dto.response.barTable.BarTableManagementResponse;
import com.ducnt.chillshaker.dto.response.barTable.BarTableResponse;
import com.ducnt.chillshaker.dto.response.barTable.BarTableStatusResponse;
import com.ducnt.chillshaker.enums.BarTableStatusEnum;
import com.ducnt.chillshaker.enums.BookingStatusEnum;
import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import com.ducnt.chillshaker.exception.ExistDataException;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.BarTable;
import com.ducnt.chillshaker.model.BookingTable;
import com.ducnt.chillshaker.model.TableType;
import com.ducnt.chillshaker.repository.BarTableRepository;
import com.ducnt.chillshaker.repository.BookingTableRepository;
import com.ducnt.chillshaker.repository.GenericSpecification;
import com.ducnt.chillshaker.repository.TableTypeRepository;
import com.ducnt.chillshaker.service.thirdparty.RedisService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BarTableService {
    BarTableRepository barTableRepository;
    BookingTableRepository bookingTableRepository;
    TableTypeRepository tableTypeRepository;
    ModelMapper modelMapper;
    GenericSpecification<BarTable> genericSpecification;
    RedisService redisService;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public BarTableResponse createBarTable(BarTableCreationRequest request) {
        try{
            if(barTableRepository.existsByName(request.getName()))
                throw new ExistDataException("Table's name is existed");

            TableType tableType = tableTypeRepository.findById(request.getTableTypeId())
                    .orElseThrow(() -> new NotFoundException("Table type is not existed"));

            BarTable barTable = modelMapper.map(request, BarTable.class);
            barTable.setTableType(tableType);
            barTableRepository.save(barTable);
            return modelMapper.map(barTable, BarTableResponse.class);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (ExistDataException ex) {
            throw new ExistDataException(ex.getMessage());
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public BarTableResponse updateBarTable(UUID id, BarTableUpdateRequest request) {
        try {
            BarTable barTable = barTableRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Table is not existed"));

            TableType tableType = tableTypeRepository.findById(request.getTableTypeId())
                    .orElseThrow(() -> new NotFoundException("Table type is not existed"));

            modelMapper.map(request, barTable);
            barTable.setTableType(tableType);
            barTableRepository.save(barTable);
            return modelMapper.map(barTable, BarTableResponse.class);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public boolean deleteBarTableById(UUID id) {
        try {
            BarTable barTable = barTableRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Table is not existed"));
            barTable.setDeleted(true);
            barTableRepository.save(barTable);
            return true;
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    public Page<BarTableResponse> getAllBarTableForCustomer(
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
            long numOfBarTable = barTableRepository.count();

            Page<BarTable> barTables = barTableRepository.findAllByIsDeletedFalse(pageRequest);

            List<BarTableResponse> barTableResponses = barTables.getContent().stream()
                    .map((element) -> modelMapper.map(element, BarTableResponse.class)).toList();

            return new PageImpl<>(barTableResponses, barTables.getPageable(), numOfBarTable);
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<BarTableManagementResponse> getAllBarTableForAdmin(
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
            var filter = genericSpecification.getFilters(q, includeProperties, attribute);
            long numOfBarTable = barTableRepository.count();

            Page<BarTable> barTables = barTableRepository.findAll(filter, pageRequest);

            List<BarTableManagementResponse> barTableResponses = barTables.getContent().stream()
                    .map((element) -> modelMapper.map(element, BarTableManagementResponse.class)).toList();

            return new PageImpl<>(barTableResponses, barTables.getPageable(), numOfBarTable);
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    public BarTableResponse getBarTableById(UUID id) {
        try {
            BarTable barTable = barTableRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Table is not existed"));

            return modelMapper.map(barTable, BarTableResponse.class);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    public List<BarTableResponse> getBarTableByDateTime(LocalDate bookingDate, LocalTime bookingTime) {
        try {
            List<BookingTable> bookingTables = bookingTableRepository
                    .findAllByBookingBookingDateAndBookingBookingTimeAndBookingStatus(bookingDate, bookingTime, BookingStatusEnum.PENDING);
            List<BarTable> barTables = barTableRepository.findAll();
            List<BarTableResponse> barTableResponses = barTables.stream().map(barTable -> {
                //boolean isReservation = bookingTables.stream().anyMatch(bookingTable -> bookingTable.getBarTable().equals(barTable));
                String key = barTable.getName() + "-" + bookingDate + "-" + bookingTime;
                String barTableRedis = redisService.getBarTableStatus(key);
                if(barTableRedis != null) {
                    String[] split = barTableRedis.split("#");
                    barTable.setStatus(BarTableStatusEnum.valueOf(split[1]));
                }
                return modelMapper.map(barTable, BarTableResponse.class);
            }).toList();

            return barTableResponses;
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    public BarTableStatusResponse getStatusBarTable(BarTableStatusRequest request) {
        BarTable barTable = barTableRepository.findById(request.getBarTableId())
                .orElseThrow(() -> new NotFoundException("Bar table is not existed"));

        String key = barTable.getName() + "-" + request.getBookingDate() + "-" + request.getBookingTime();
        String barTableInRedis = redisService.getBarTableStatus(key);

        if(barTableInRedis != null) {
            String[] barTableSplit = barTableInRedis.split("#");
            return BarTableStatusResponse
                    .builder()
                    .id(UUID.fromString(barTableSplit[0]))
                    .statusEnum(BarTableStatusEnum.valueOf(barTableSplit[1]))
                    .build();
        }
        return new BarTableStatusResponse(barTable.getId(), BarTableStatusEnum.EMPTY, request.getUserEmail());
    }

    public BarTableStatusResponse setStatusBarTable(BarTableStatusRequest request) {
        BarTable barTable = barTableRepository.findById(request.getBarTableId())
                .orElseThrow(() -> new NotFoundException("Bar table is not existed"));
        long duration = 300;
        String key = barTable.getName() + "-" + request.getBookingDate() + "-" + request.getBookingTime();

        String barTableStatus = redisService.getBarTableStatus(key);

        if(barTableStatus != null) {
            if(BarTableStatusEnum.EMPTY.equals(request.getStatus())) {
                redisService.deleteBarTableStatus(key);
            }

            String[] valueSplit = barTableStatus.split("#");

            if(BarTableStatusEnum.RESERVED.name().equals(valueSplit[1]) &&
                    !request.getUserEmail().equals(valueSplit[2])) {
                throw new CustomException(ErrorResponse.BAR_TABLE_WITH_WRONG_USER_HOLD);
            }

            if(BarTableStatusEnum.RESERVED.name().equals(valueSplit[1]) ||
                    BarTableStatusEnum.SERVING.name().equals(valueSplit[1]))
            {
                return null;
            } else {
                redisService.deleteBarTableStatus(key);
            }
        }

        String value = barTable.getId() + "#" + request.getStatus() + "#" + request.getUserEmail();
        redisService.saveBarTableStatus(key, value, duration);
        return BarTableStatusResponse
                .builder()
                .id(request.getBarTableId())
                .statusEnum(request.getStatus())
                .userEmail(request.getUserEmail())
                .build();
    }

}
