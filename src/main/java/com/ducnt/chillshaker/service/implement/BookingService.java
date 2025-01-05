package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.config.CustomJwtDecoder;
import com.ducnt.chillshaker.dto.request.booking.*;
import com.ducnt.chillshaker.dto.response.barTable.BarTableResponse;
import com.ducnt.chillshaker.dto.response.booking.BookingResponse;
import com.ducnt.chillshaker.dto.response.booking.BookingTableResponse;
import com.ducnt.chillshaker.dto.response.booking.ResponseWithPaymentLink;
import com.ducnt.chillshaker.enums.BookingStatusEnum;
import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.*;
import com.ducnt.chillshaker.repository.*;
import com.ducnt.chillshaker.service.thirdparty.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookingService {
    BookingRepository bookingRepository;
    BarTableRepository barTableRepository;
    DrinkRepository drinkRepository;
    AccountRepository accountRepository;
    ModelMapper modelMapper;
    VNPayService vnPayService;
    CustomJwtDecoder jwtDecoder;
    private final MenuRepository menuRepository;

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
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @Transactional
    public ResponseWithPaymentLink createBookingTableWithDrink(HttpServletRequest servletRequest,
                                                                BookingTableWithDrinkRequest request) {
        try {
            Booking booking = createGenericBookingObject(servletRequest, request);
            booking.setBookingType(request.getBookingType());

            double totalPrice = booking.getTotalPrice();
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
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

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

            double totalPrice = booking.getTotalPrice() + menu.getPrice();

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
        Booking booking = modelMapper.map(request, Booking.class);

        for (UUID tableId : request.getTableIds()) {
            BarTable barTable = barTableRepository.findById(tableId)
                    .orElseThrow(() -> new NotFoundException("Table is not existed"));
            BookingTable bookingTable = new BookingTable(booking, barTable);
            totalPrice += barTable.getTableType().getDepositAmount();
            booking.getBookingTables().add(bookingTable);
        }

        LocalDateTime expireTime = LocalDateTime.of(request.getBookingDate(), request.getBookingTime())
                .plusHours(2);
        booking.setExpireAt(expireTime);
        booking.setAccount(account);
        booking.setBookingCode(generateBookingCode(request.getBookingDate()));
        booking.setStatus(BookingStatusEnum.PENDING.ordinal());
        booking.setTotalPrice(totalPrice);
        return booking;
    }

    private String generateBookingCode(LocalDate bookingDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = bookingDate.format(dateTimeFormatter);
        String randomPart = String.format("%06d", new Random().nextInt(999999));
        return datePart + randomPart;
    }

}
