package com.my.demo.wallet.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Repository
@Slf4j
public class RedisClientTemplate {
    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取键值
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Jedis with exception : {}", e.getMessage());
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置超时时间
     * @param seconds
     * @param key
     * @return
     */
    public Long expire(String key, int seconds) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.expire(key, seconds);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Jedis with exception : {}", e.getMessage());
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置键值
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            value = value.replace("\\", "");
            result = jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Jedis with exception : {}", e.getMessage());
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置键值及超时时间
     * @param key
     * @param value
     * @return
     */
    public String setex(String key, int seconds, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Jedis with exception : {}", e.getMessage());
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }

    /**
     * 删除键值
     * @param key
     * @return
     */
    public Long del(String key) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Jedis with exception : {}", e.getMessage());
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }

    public Long hset(String key, String field, String value) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hset(key, field, value);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Jedis with exception : {}", e.getMessage());
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }

    public String hget(String key, String field) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Jedis with exception : {}", e.getMessage());
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }

    /**
     * 是否含有key
     * @param key
     * @return
     */
    public  boolean exists(String key){
        Jedis jedis = null;
        Boolean result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Jedis with exception : {}", e.getMessage());
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }
}
