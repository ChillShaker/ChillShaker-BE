package com.ducnt.chillshaker.service.interfaces;

public interface IRedisService {
    void saveOTP(String email, String otp, long duration);

    String getOTP(String email);

    void deleteOTP(String email);

    void saveBarTableStatus(String key, String barTableId, long duration);

    String getBarTableStatus(String key);

    void deleteBarTableStatus(String key);
}
