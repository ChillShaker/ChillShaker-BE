package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.dto.response.payment.PaymentResponse;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.Payment;
import com.ducnt.chillshaker.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService implements com.ducnt.chillshaker.service.interfaces.IPaymentService {
    PaymentRepository paymentRepository;
    ModelMapper modelMapper;

    @Override
    public PaymentResponse getPaymentInfo(String transactionNo) {
        try {
            Payment payment = paymentRepository.findByTransactionCode(transactionNo)
                    .orElseThrow(() -> new NotFoundException("Payment not found"));
            PaymentResponse paymentResponse= modelMapper.map(payment, PaymentResponse.class);
            paymentResponse.setEmail(payment.getAccount().getEmail());
            return paymentResponse;
        } catch (NotFoundException ex) {
            return null;
        }
    }
}
