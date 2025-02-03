package com.ducnt.chillshaker.service.thirdparty;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RedisService {
    StringRedisTemplate redisTemplate;

    public void saveOTP(String email, String otp, long duration) {
        redisTemplate.opsForValue().set(email, otp, duration, TimeUnit.MINUTES);
    }

    public String getOTP(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    public void deleteOTP(String email) {
        redisTemplate.delete(email);
    }

    public void saveBarTableStatus(String key, String barTableId, long duration) {
        redisTemplate.opsForValue().set(key, barTableId, duration, TimeUnit.SECONDS);
    }

    public String getBarTableStatus(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteBarTableStatus(String key) {
        redisTemplate.delete(key);
    }
}
