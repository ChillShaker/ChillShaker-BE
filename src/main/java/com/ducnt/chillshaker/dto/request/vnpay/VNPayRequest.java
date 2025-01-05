package com.ducnt.chillshaker.dto.request.vnpay;

import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import com.ducnt.chillshaker.util.HmacUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VNPayRequest {
    final SortedMap<String, String> requestData = new TreeMap<>();

    String secretKey;
    String baseUrl;
    String vnp_TmnCode;
    String vnp_Version;
    String vnp_ReturnUrl;
    String vnp_Locale;
    String vnp_Command;
    String vnp_IpAddr;
    String vnp_CurrCode;
    String vnp_CreateDate;
    Long vnp_Amount;
    String vnp_OrderType;
    String vnp_OrderInfo;
    String vnp_TxnRef;
    String vnp_ExpireDate;

    public VNPayRequest(Environment environment, LocalDateTime createDate, HttpServletRequest servletRequest,
                        long amount, String currCode, String orderType, String orderInfo,
                        String txnRef) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        this.vnp_CreateDate = createDate.format(dateTimeFormatter);
        this.vnp_IpAddr = getIpAddress(servletRequest);
        this.vnp_Amount = amount * 100;
        this.vnp_CurrCode = currCode;
        this.vnp_OrderType = orderType;
        this.vnp_OrderInfo = orderInfo;
        this.vnp_TxnRef = txnRef;
        this.secretKey = environment.getProperty("vnpay.hash-secret");
        this.baseUrl = environment.getProperty("vnpay.payment-url");
        this.vnp_TmnCode = environment.getProperty("vnpay.tmn-code");
        this.vnp_Version = environment.getProperty("vnpay.version");
        this.vnp_ReturnUrl = environment.getProperty("vnpay.return-url");
        this.vnp_Locale = "vn";
        this.vnp_Command = "pay";
    }

    public String getLink() throws NoSuchAlgorithmException {
        try {
            makeRequestData();
            StringBuilder data = new StringBuilder();
            for(Map.Entry<String, String> entry : requestData.entrySet()) {
                if(entry.getValue() != null && !entry.getValue().isEmpty()) {
                    data.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                            .append("&");
                }
            }
            String queryString = data.substring(0, data.length() - 1);
            String secureHash = HmacUtil.hmacSHA512(secretKey, queryString);
            return baseUrl + "?" + queryString + "&vnp_SecureHash=" +secureHash;
        } catch (NoSuchAlgorithmException ex) {
            throw new CustomException(ErrorResponse.PAYMENT_INVALID);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new CustomException(ErrorResponse.UNKNOWN_ERROR_IN_PAYMENT);
        }
    }

    private void makeRequestData() {
        if (vnp_Amount != null) requestData.put("vnp_Amount", String.valueOf(vnp_Amount));
        if (vnp_Command != null) requestData.put("vnp_Command", vnp_Command);
        if (vnp_CreateDate != null) requestData.put("vnp_CreateDate", vnp_CreateDate);
        if (vnp_CurrCode != null) requestData.put("vnp_CurrCode", vnp_CurrCode);
        if (vnp_IpAddr != null) requestData.put("vnp_IpAddr", vnp_IpAddr);
        if (vnp_Locale != null) requestData.put("vnp_Locale", vnp_Locale);
        if (vnp_OrderInfo != null) requestData.put("vnp_OrderInfo", vnp_OrderInfo);
        if (vnp_OrderType != null) requestData.put("vnp_OrderType", vnp_OrderType);
        if (vnp_ReturnUrl != null) requestData.put("vnp_ReturnUrl", vnp_ReturnUrl);
        if (vnp_TmnCode != null) requestData.put("vnp_TmnCode", vnp_TmnCode);
        if (vnp_ExpireDate != null) requestData.put("vnp_ExpireDate", vnp_ExpireDate);
        if (vnp_TxnRef != null) requestData.put("vnp_TxnRef", vnp_TxnRef);
        if (vnp_Version != null) requestData.put("vnp_Version", vnp_Version);
    }

    private String getIpAddress(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("X-FORWARDED-FOR");
            if(ipAddress == null) {
                ipAddress = request.getLocalAddr();
            }
        } catch (Exception e) {
            ipAddress = "Invalid IP:" + e.getMessage();
        }
        return ipAddress;
    }
}
