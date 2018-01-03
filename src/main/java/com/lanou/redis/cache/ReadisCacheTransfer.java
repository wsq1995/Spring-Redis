package com.lanou.redis.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import javax.annotation.Resource;

/**
 * Created by dllo on 17/12/29.
 */

//这是个中间类,负责给redisCache装配工厂对象
public class ReadisCacheTransfer {

    @Resource
    public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        RedisCache.setJedisConnectionFactroy(jedisConnectionFactory);
    }

















}
