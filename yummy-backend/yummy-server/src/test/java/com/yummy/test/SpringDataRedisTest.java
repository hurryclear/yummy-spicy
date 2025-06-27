package com.yummy.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedisTemplate() {
        System.out.println(redisTemplate);
        // for String
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // for Hash
        HashOperations hashOperations = redisTemplate.opsForHash();
        // for List
        ListOperations listOperations = redisTemplate.opsForList();
        // for Set
        SetOperations setOperations = redisTemplate.opsForSet();
        // for sorted Set (Zset)
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
    }

    @Test
    public void testSpring() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("date", "today");
        valueOperations.set("weather", "sunny");
        String date = (String) valueOperations.get("date");
        System.out.println(date);
        // set with ttl
        valueOperations.set("bbq", "tomorrow", 3, TimeUnit.SECONDS);
        // set the key-value when the key doesn't exist
        valueOperations.setIfAbsent("date", "yesterday");
    }

    @Test
    public void testHash() {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("1", "name", "jiang");
        hashOperations.put("1", "age", "30");
        String name = (String) hashOperations.get("1", "name");
        System.out.println(name);

        // get all fields of key "1"
        System.out.println(hashOperations.keys("1"));
        // get all values of key "1"
        System.out.println(hashOperations.values("1"));
    }

    @Test
    public void testList() {
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPushAll("mylist", "a", "b", "c");
        String data = (String) listOperations.index("mylist", 0);
        System.out.println(data);
    }

    @Test
    public void testSet() {
        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.add("nameofperson", "jiang", "huo", "llll");
        System.out.println(setOperations.size("nameofperson"));
        System.out.println(setOperations.pop("nameofperson"));
        System.out.println(setOperations.size("nameofperson"));
    }
}

