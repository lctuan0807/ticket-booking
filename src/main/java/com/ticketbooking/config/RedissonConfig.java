package com.ticketbooking.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.data.redis.autoconfigure.DataRedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

  @Bean(destroyMethod = "shutdown")
  public RedissonClient redissonClient(DataRedisProperties redisProperties) {
    Config config = new Config();
    String address = "redis://" + redisProperties.getHost() + ":" + redisProperties.getPort();
    config.useSingleServer().setAddress(address);
    return Redisson.create(config);
  }
}
