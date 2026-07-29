package com.ticketbooking.redis;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class RedisServiceImpl implements RedisService {
  private final RedisTemplate<String, Object> redisTemplate;

  public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public void setString(String key, String value) {
    if (!StringUtils.hasLength(key)) {
      return;
    }

    redisTemplate.opsForValue().set(key, value);
  }

  @Override
  public String getString(String key) {
    return Optional.ofNullable(redisTemplate.opsForValue().get(key))
        .map(Object::toString)
        .orElse(null);
  }

  @Override
  public void setObject(String key, Object value) {
    if (!StringUtils.hasLength(key)) {
      return;
    }

    try {
      redisTemplate.opsForValue().set(key, value);
    } catch (Exception e) {
      log.error("Failed to set object in Redis", e);
    }

  }

  @Override
  public void setObject(String key, Object value, Duration ttl) {
    if (!StringUtils.hasLength(key)) {
      return;
    }

    try {
      redisTemplate.opsForValue().set(key, value, ttl);
    } catch (Exception e) {
      log.error("Failed to set object in Redis", e);
    }
  }

  @Override
  public <T> T getObject(String key, Class<T> targetClass) {
    Object result = redisTemplate.opsForValue().get(key);
    if (result == null) {
      return null;
    }
    if (targetClass.isInstance(result)) {
      return targetClass.cast(result);
    }
    if (result instanceof Map) {
      try {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(result, targetClass);
      } catch (Exception e) {
        log.error("Error converting LinkedHashMap to object: {}", e.getMessage());
        return null;
      }
    }

    if (result instanceof String) {
      try {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue((String) result, targetClass);
      } catch (Exception e) {
        log.error("Error deserializing JSON to object: {}", e.getMessage());
        return null;
      }
    }

    return null;
  }

  @Override
  public void delete(String key) {
    redisTemplate.delete(key);
  }
}
