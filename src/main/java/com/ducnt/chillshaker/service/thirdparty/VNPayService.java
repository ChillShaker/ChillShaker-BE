package com.ducnt.chillshaker.service.thirdparty;

import com.ducnt.chillshaker.dto.request.vnpay.VNPayRequest;
import com.ducnt.chillshaker.dto.response.vnpay.VNPayResponse;
import com.ducnt.chillshaker.enums.BookingStatusEnum;
import com.ducnt.chillshaker.enums.PaymentStatusEnum;
import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.*;
import com.ducnt.chillshaker.repository.PaymentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VNPayService {
    PaymentRepository paymentRepository;
    EntityManager entityManager;
    Environment environment;
    RedisService redisService;

    @Transactional
    public String getVnpayPaymentLink(UUID bookingId, UUID accountId, double totalPrice,
                                      HttpServletRequest servletRequest) {
        try {
            Booking booking = entityManager.getReference(Booking.class, bookingId);
            Account account = entityManager.getReference(Account.class, accountId);
            LocalDateTime currentTime = LocalDateTime.now();

            Payment payment = Payment
                    .builder()
                    .booking(booking)
                    .account(account)
                    .totalPrice(totalPrice)
                    .paymentDate(currentTime.toLocalDate())
                    .status(PaymentStatusEnum.PENDING)
                    .build();

            booking.setPayment(payment);
            account.getPayment().add(payment);
            paymentRepository.save(payment);

            VNPayRequest vnPayRequest = new VNPayRequest(
                    environment,
                    currentTime,
                    servletRequest,
                    (long) totalPrice,
                    "VND",
                    "other",
                    "Thanh toán cho đơn đặt bàn",
                    payment.getId().toString()
            );

            return vnPayRequest.getLink();
        } catch (EntityNotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            throw new CustomException(ErrorResponse.PAYMENT_INVALID);
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @Transactional
    public boolean processPaymentReturn(VNPayResponse vnPayResponse) {
        try {
            vnPayResponse.setSecretKey(environment.getProperty("vnpay.hash-secret"));
            boolean valid = vnPayResponse.isValid();
            if(valid && !vnPayResponse.getVnp_TxnRef().isEmpty()) {
                Payment payment = paymentRepository.findById(UUID.fromString(vnPayResponse.getVnp_TxnRef()))
                        .orElseThrow(() -> new NotFoundException("Payment is not existed"));
                if(!Objects.equals(vnPayResponse.getVnp_ResponseCode(), "00") ||
                        !Objects.equals(vnPayResponse.getVnp_TransactionStatus(), "00")) {

                    removeBarTablesInRedis(payment.getBooking().getBookingTables());

                    payment.setStatus(PaymentStatusEnum.FAIL);
                    payment.getBooking().setStatus(BookingStatusEnum.CANCELED);
                    paymentRepository.save(payment);
                    return false;
                }

                payment.setStatus(PaymentStatusEnum.SUCCESS);
                payment.setTransactionCode(vnPayResponse.getVnp_TransactionNo());
                payment.setProviderName(vnPayResponse.getVnp_BankCode());
                paymentRepository.save(payment);
                return true;
            } else {
                throw new NoSuchAlgorithmException();
            }
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            throw new CustomException(ErrorResponse.PAYMENT_INVALID);
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    private void removeBarTablesInRedis(Collection<BookingTable> bookingTables) {
        bookingTables.forEach(e -> {
           String key = e.getBarTable().getName() + "-" + e.getBooking().getBookingDate() + "-" + e.getBooking().getBookingTime();
           redisService.deleteBarTableStatus(key);
        });
    }
}
