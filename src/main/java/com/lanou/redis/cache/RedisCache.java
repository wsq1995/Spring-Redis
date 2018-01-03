package com.lanou.redis.cache;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * Created by dllo on 17/12/29.
 */
public class RedisCache implements Cache {

    private final String id;

    private static JedisConnectionFactory jedisConnectionFactory;

    //    读写锁
    private ReadWriteLock readWriteLock;

    //    必须在jedisConnectionFactroy的下面,要不会报错
    public static void setJedisConnectionFactroy(JedisConnectionFactory jedisConnectionFactory) {
        RedisCache.jedisConnectionFactory = jedisConnectionFactory;
    }

    //    id的构造方法
    public RedisCache(String id) {

        if (id == null) {
            throw new IllegalArgumentException("id不能为空");
        }
        this.id = id;
    }

    //    获取缓存对象的唯一标识
    @Override
    public String getId() {
        return this.id;
    }

    //    保存key-value到缓存对象中,
    //    key可以使任何对象,但一般是CacheKey对象,
    //    value是查询结果,一般是list
    @Override
    public void putObject(Object key, Object value) {
        JedisConnection jedisConnection = null;
        try {
            //通过工厂创建一个redis的连接
            jedisConnection = (JedisConnection) jedisConnectionFactory.getConnection();

            // 创建序列化工具类,用于将实现了serializable接口的对象转为byte数组
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
            // 通过set方法将key,value写入到redis
            jedisConnection.set(serializer.serialize(key), serializer.serialize(value));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedisConnection != null) {
                jedisConnection.close();
            }
        }
    }

    @Override
    public Object getObject(Object key) {
        Object result = null;
        JedisConnection connection = null;
        try {
//        通过工厂创建一个redis的连接
            connection = (JedisConnection) jedisConnectionFactory.getConnection();

//            创建序列化工具类,用于实现了serializable接口额对象转为byte[]
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();

            result = serializer.deserialize(connection.get(serializer.serialize(key)));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    //    根据key移出value,可选,没有被核心框架调用
    @Override
    public Object removeObject(Object key) {

        Object result = null;
        JedisConnection connection = null;

        try {
//        通过工厂创建一个redis的连接
            connection = (JedisConnection) jedisConnectionFactory.getConnection();

//            创建序列化工具类,用于实现了serializable接口额对象转为byte[]
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();

//            根据key删除key-value键值对,0就是立即删除
            connection.expire(serializer.serialize(key), 0);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    @Override
    public void clear() {

        JedisConnection connection = null;

        try {
//        通过工厂创建一个redis的连接
            connection = (JedisConnection) jedisConnectionFactory.getConnection();

            connection.flushAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public int getSize() {
        JedisConnection connection = null;
        Integer count = 0;
        try {
//        通过工厂创建一个redis的连接
            connection = (JedisConnection) jedisConnectionFactory.getConnection();

            count = connection.dbSize().intValue();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return count;
    }


    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }


}
