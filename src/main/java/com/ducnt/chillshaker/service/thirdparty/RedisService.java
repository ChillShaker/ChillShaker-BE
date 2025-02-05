package com.ducnt.chillshaker.service.thirdparty;

import com.ducnt.chillshaker.service.interfaces.IRedisService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RedisService implements IRedisService {
    StringRedisTemplate redisTemplate;

    @Override
    public void saveOTP(String email, String otp, long duration) {
        redisTemplate.opsForValue().set(email, otp, duration, TimeUnit.MINUTES);
    }

    @Override
    public String getOTP(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    @Override
    public void deleteOTP(String email) {
        redisTemplate.delete(email);
    }

    @Override
    public void saveBarTableStatus(String key, String value, long duration) {
        redisTemplate.opsForValue().set(key, value, duration, TimeUnit.SECONDS);
    }

    @Override
    public String getBarTableStatus(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void deleteBarTableStatus(String key) {
        redisTemplate.delete(key);
    }
}
