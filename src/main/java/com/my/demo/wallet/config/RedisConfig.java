package com.my.demo.wallet.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableRedisHttpSession
@Slf4j
public class RedisConfig {
    @Autowired
    private RedisProperties properties;

    private static RedisTemplate redisTemplate;

    public static RedisTemplate get() {
        return redisTemplate;
    }

    @Bean(value = "jedisPool")
    public JedisPool getJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(properties.getJedis().getPool().getMaxActive());
        config.setMaxIdle(properties.getJedis().getPool().getMaxIdle());
        config.setMinIdle(properties.getJedis().getPool().getMinIdle());
        config.setMaxWaitMillis(properties.getJedis().getPool().getMaxWait().toMillis());
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        config.setTestWhileIdle(true);
        config.setTimeBetweenEvictionRunsMillis(30000);
        config.setNumTestsPerEvictionRun(10);
        config.setMinEvictableIdleTimeMillis(60000);
        JedisPool pool = new JedisPool(
                config,
                properties.getHost(),
                properties.getPort(),
                (int) (properties.getTimeout().getSeconds() * 1000L),
                properties.getPassword(),
                properties.getDatabase());
        return pool;
    }
}
