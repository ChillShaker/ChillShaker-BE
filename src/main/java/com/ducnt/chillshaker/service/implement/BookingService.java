package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.config.authentication.CustomJwtDecoder;
import com.ducnt.chillshaker.dto.request.booking.*;
import com.ducnt.chillshaker.dto.response.booking.BookingResponse;
import com.ducnt.chillshaker.dto.response.booking.ResponseWithPaymentLink;
import com.ducnt.chillshaker.enums.BarTableStatusEnum;
import com.ducnt.chillshaker.enums.BookingStatusEnum;
import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.*;
import com.ducnt.chillshaker.repository.*;
import com.ducnt.chillshaker.service.interfaces.IRedisService;
import com.ducnt.chillshaker.service.interfaces.IVNPayService;
import com.ducnt.chillshaker.service.thirdparty.RedisService;
import com.ducnt.chillshaker.service.thirdparty.VNPayService;
import com.ducnt.chillshaker.util.TimeValidatorUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookingService implements com.ducnt.chillshaker.service.interfaces.IBookingService {
    BookingRepository bookingRepository;
    BarTableRepository barTableRepository;
    BarRepository barRepository;
    DrinkRepository drinkRepository;
    AccountRepository accountRepository;
    ModelMapper modelMapper;
    IVNPayService vnPayService;
    CustomJwtDecoder jwtDecoder;
    IRedisService redisService;
    MenuRepository menuRepository;

    @Override
    @Transactional
    public ResponseWithPaymentLink createBookingTableOnly(HttpServletRequest servletRequest,
                                                          BookingTableOnlyRequest request) {
        try {
            Booking booking = createGenericBookingObject(servletRequest, request);
            booking.setBookingType(request.getBookingType());

            bookingRepository.save(booking);

            String paymentLink = vnPayService.getVnpayPaymentLink(booking.getId(), booking.getAccount().getId(),
                    booking.getTotalPrice(), servletRequest);

            return ResponseWithPaymentLink
                    .builder()
                    .bookingResponse(modelMapper.map(booking, BookingResponse.class))
                    .paymentLink(paymentLink)
                    .build();
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (CustomException ex) {
            throw new CustomException(ex.getErrorResponse());
        } catch (RedisSystemException ex) {
            throw new CustomException(ErrorResponse.REDIS_STORE_FAIL);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @Override
    @Transactional
    public ResponseWithPaymentLink createBookingTableWithDrink(HttpServletRequest servletRequest,
                                                               BookingTableWithDrinkRequest request) {
        try {
            Booking booking = createGenericBookingObject(servletRequest, request);
            booking.setBookingType(request.getBookingType());

            double totalPrice = 0;
            for (BookingDrinkRequest drinkRequest : request.getDrinks()) {
                Drink drink = drinkRepository.findById(drinkRequest.getDrinkId())
                        .orElseThrow(() -> new NotFoundException("Drink is not existed"));
                totalPrice += drinkRequest.getQuantity() * drink.getPrice();
                BookingDrink bookingDrink = new BookingDrink(drink.getPrice(), drinkRequest.getQuantity(),
                        booking, drink);
                booking.getBookingDrinks().add(bookingDrink);
            }
            booking.setTotalPrice(totalPrice);

            bookingRepository.save(booking);

            String paymentLink = vnPayService.getVnpayPaymentLink(booking.getId(), booking.getAccount().getId(),
                    booking.getTotalPrice(), servletRequest);

            return ResponseWithPaymentLink
                    .builder()
                    .bookingResponse(modelMapper.map(booking, BookingResponse.class))
                    .paymentLink(paymentLink)
                    .build();
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (CustomException ex) {
            throw new CustomException(ex.getErrorResponse());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @Override
    @Transactional
    public ResponseWithPaymentLink createBookingTableWithMenu(HttpServletRequest servletRequest,
                                                              BookingTableWithMenuRequest request) {
        try {
            Booking booking = createGenericBookingObject(servletRequest, request);
            booking.setBookingType(request.getBookingType());

            Menu menu = menuRepository.findById(request.getMenuId())
                    .orElseThrow(() -> new NotFoundException("Menu is not existed"));
            BookingMenu bookingMenu = new BookingMenu(booking, menu);
            booking.getBookingMenus().add(bookingMenu);

            booking.setTotalPrice(menu.getPrice());

            bookingRepository.save(booking);

            String paymentLink = vnPayService.getVnpayPaymentLink(booking.getId(), booking.getAccount().getId(),
                    booking.getTotalPrice(), servletRequest);

            return ResponseWithPaymentLink
                    .builder()
                    .bookingResponse(modelMapper.map(booking, BookingResponse.class))
                    .paymentLink(paymentLink)
                    .build();
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (CustomException ex) {
            throw new CustomException(ex.getErrorResponse());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    private Booking createGenericBookingObject(HttpServletRequest servletRequest,
                                               GenericBookingRequest request) {
        double totalPrice = 0;
        String token = servletRequest.getHeader("Authorization").replace("Bearer ", "");
        Jwt decode = jwtDecoder.decode(token);

        Account account = accountRepository.findByEmail(decode.getSubject())
                .orElseThrow(() -> new NotFoundException("Email not found"));

        Bar bar = barRepository.findByName(request.getBarName())
                .orElseThrow(() -> new NotFoundException("Bar is not existed"));

        boolean isValid = TimeValidatorUtil.validateBookingDateTime(request.getBookingDate(),
                request.getBookingTime(), bar.getBarTimes()).orElseThrow(() -> new CustomException(ErrorResponse.TIME_INVALID));

        if(isValid) {
            Booking booking = modelMapper.map(request, Booking.class);
            LocalDateTime localDateTime = LocalDateTime.of(request.getBookingDate(), request.getBookingTime());

            for (UUID tableId : request.getTableIds()) {
                BarTable barTable = barTableRepository.findById(tableId)
                        .orElseThrow(() -> new NotFoundException("Table is not existed"));

                String key = barTable.getName() + "-" + request.getBookingDate() + "-" + request.getBookingTime();
                long currentEpochSeconds = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
                long bookingEpochSeconds = localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();

                String value = barTable.getId() + "#" + BarTableStatusEnum.RESERVED + "#" + account.getEmail();
                redisService.saveBarTableStatus(key, value,
                        bookingEpochSeconds - currentEpochSeconds);

                BookingTable bookingTable = new BookingTable(booking, barTable);
                totalPrice += barTable.getTableType().getDepositAmount();
                booking.getBookingTables().add(bookingTable);
            }

            booking.setBar(bar);
            booking.setExpireAt(localDateTime.plusHours(2));
            booking.setAccount(account);
            booking.setBookingCode(generateBookingCode(request.getBookingDate()));
            booking.setStatus(BookingStatusEnum.PENDING);
            booking.setTotalPrice(totalPrice);
            return booking;
        } else {
            throw new CustomException(ErrorResponse.TIME_INVALID);
        }
    }

    private String generateBookingCode(LocalDate bookingDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = bookingDate.format(dateTimeFormatter);
        String randomPart = String.format("%06d", new Random().nextInt(999999));
        return datePart + randomPart;
    }

    @Override
    public BookingResponse getBookingInfoById(UUID id) {
        try {
            var booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Booking is not existed"));
            return modelMapper.map(booking, BookingResponse.class);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('STAFF')")
    public BookingResponse updateBookingStatus(UUID id, BookingStatusEnum statusEnum) {
        try {
            var booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Booking is not existed"));
            long duration = TimeUnit.HOURS.toMillis(24);

            for(BookingTable bookingTable : booking.getBookingTables()) {
                BarTable barTable = bookingTable.getBarTable();
                String key = barTable.getName() + "-" + booking.getBookingDate() + "-" + booking.getBookingTime();
                if(redisService.getBarTableStatus(key) != null) {
                    redisService.deleteBarTableStatus(key);
                }
                String value = barTable.getId() + "#" + statusEnum.name()+ "#" + booking.getAccount().getEmail();
                redisService.saveBarTableStatus(key, value, duration);
            }

            return modelMapper.map(booking, BookingResponse.class);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @Override
    @PreAuthorize("hasRole('STAFF')")
    public List<BookingResponse> getAllBookingByDateTime(LocalDate bookingDate, LocalTime bookingTime) {
        try {
            var bookings = bookingRepository.findAll();

            List<BookingResponse> responses = bookings.stream()
                    .filter(booking -> booking.getBookingTime().isAfter(bookingTime)
                            || booking.getBookingDate().isAfter(bookingDate))
                    .map(booking -> modelMapper.map(booking, BookingResponse.class))
                    .toList();
            return responses;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }
}
