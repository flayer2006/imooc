package com.yongh.mooc.all.seckill.utils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.yongh.mooc.all.seckill.entity.Seckill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 描述: Jedis 工具类
 *
 * @author soitis
 * @create 2019-08-30 16:10
 */
@Component
@Slf4j
public class RedisUtil {
    @Autowired
    JedisPool jedisPool;

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom (Seckill.class);

    /**
     * 向Redis中存值，永久有效
     */
    public String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            return "0";
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    public String putSeckill(Seckill seckill){
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource ();
            String key = "seckill:" + seckill.getSeckillId();
            try {
                byte[] bytes = ProtobufIOUtil.toByteArray (seckill,schema,
                        LinkedBuffer.allocate (LinkedBuffer.DEFAULT_BUFFER_SIZE));

                int timeout = 60*60;
                String result = jedis.setex (key.getBytes (),timeout,bytes);
                return result;

            } finally {
                returnResource(jedisPool, jedis);
            }
        } catch (Exception e) {
            log.error (e.getMessage (),e);
        }
        return null;
    }



    /**
     * 根据传入Key获取指定Value
     */
    public String get(String key) {
        Jedis jedis = null;
        String value;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            return "0";
        } finally {
            returnResource(jedisPool, jedis);
        }
        return value;
    }

    public Seckill getSeckill(Long seckillId){
        Jedis jedis = null;
        String value;
        jedis = jedisPool.getResource();
        String key = "seckill:" + seckillId;
        try {
            value = jedis.get(key);
            System.out.println ("test--"+value+"--test");
            byte[] bytes = jedis.get (key.getBytes ());
            if(bytes!=null){
                Seckill seckill = schema.newMessage ();
                ProtobufIOUtil.mergeFrom (bytes,seckill,schema);

                return seckill;
            }
        } finally {
            returnResource(jedisPool, jedis);
        }

        return null;
    }

    /**
     * 校验Key值是否存在
     */
    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            return false;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    /**
     * 删除指定Key-Value
     */
    public Long delete(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            return 0L;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    /**
     * <p>
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     * </p>
     *
     * @param key
     * @param value
     *            过期时间，单位：秒
     * @return 成功返回1 如果存在 和 发生异常 返回 0
     */
    public Long expire(String key, int value, int indexdb) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.expire(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }


    // 以上为常用方法，更多方法自行百度

    /**
     * 释放连接
     */
    private static void returnResource(JedisPool jedisPool, Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}