package com.my.demo.wallet.cache;

import com.my.demo.wallet.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class RedisClientTemplateTest {
    @Autowired
    private RedisClientTemplate redisClientTemplate;

    @Test
    public void testSet() {
        redisClientTemplate.set("user:test", "{\"id\":1}");
    }

    @Test
    public void testHset() {
        redisClientTemplate.hset("config:eth", "field", "value");
    }
}
