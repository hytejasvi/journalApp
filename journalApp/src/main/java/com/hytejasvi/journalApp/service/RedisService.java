package com.hytejasvi.journalApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClass) {
        try {
            log.info("Inside RedisService get method");
            Object o = redisTemplate.opsForValue().get(key);
            if (o != null) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(o.toString(), entityClass);
            }
            log.info("No value currently present in redis, hence returning null");
            return null;
        } catch (Exception e) {
            log.error("Exception ", e);
            return null;
        }
    }

    public void set(String key, Object o, Long ttl) {
        try {
            log.info("Inside RedisService Set method");
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
            log.info("Setting value into redis successful");
        } catch (Exception e) {
            log.error("Exception ", e);
        }
    }
}
