package com.ducnt.chillshaker.dto.response.vnpay;

import com.ducnt.chillshaker.util.HmacUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class VNPayResponse {
    final SortedMap<String, String> responseData = new TreeMap<>();

    String secretKey;
    String vnp_TmnCode;
    String vnp_BankCode;
    String vnp_BankTranNo;
    String vnp_CardType;
    String vnp_OrderInfo;
    String vnp_TransactionNo;
    String vnp_TransactionStatus;
    String vnp_TxnRef;
    String vnp_SecureHashType;
    String vnp_SecureHash;
    Long vnp_Amount;
    String vnp_ResponseCode;
    String vnp_PayDate;

    public boolean isValid() throws NoSuchAlgorithmException, InvalidKeyException {
        makeResponseData();

        StringBuilder data = new StringBuilder();
        for(Map.Entry<String, String> entry : responseData.entrySet()) {
            if(entry.getValue() != null && !entry.getValue().isEmpty()) {
                data.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                        .append("&");
            }
        }

        String queryString = data.substring(0, data.length() - 1);
        String checkSum = HmacUtil.hmacSHA512(secretKey, queryString);
        return checkSum.equalsIgnoreCase(vnp_SecureHash);
    }

    public void makeResponseData() {
        if (vnp_Amount != null) {
            responseData.put("vnp_Amount", String.valueOf(vnp_Amount));
        }
        if (vnp_TmnCode != null) {
            responseData.put("vnp_TmnCode", vnp_TmnCode);
        }
        if (vnp_BankCode != null) {
            responseData.put("vnp_BankCode", vnp_BankCode);
        }
        if (vnp_BankTranNo != null) {
            responseData.put("vnp_BankTranNo", vnp_BankTranNo);
        }
        if (vnp_CardType != null) {
            responseData.put("vnp_CardType", vnp_CardType);
        }
        if (vnp_OrderInfo != null) {
            responseData.put("vnp_OrderInfo", vnp_OrderInfo);
        }
        if (vnp_TransactionNo != null) {
            responseData.put("vnp_TransactionNo", vnp_TransactionNo);
        }
        if (vnp_TransactionStatus != null) {
            responseData.put("vnp_TransactionStatus", vnp_TransactionStatus);
        }
        if (vnp_TxnRef != null) {
            responseData.put("vnp_TxnRef", vnp_TxnRef);
        }
        if (vnp_PayDate != null) {
            responseData.put("vnp_PayDate", vnp_PayDate);
        }
        if (vnp_ResponseCode != null) {
            responseData.put("vnp_ResponseCode", vnp_ResponseCode);
        }
    }
}
