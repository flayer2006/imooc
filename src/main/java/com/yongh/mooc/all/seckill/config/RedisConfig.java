package com.yongh.mooc.all.seckill.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 描述: Jedis配置类
 *
 * @author soitis
 * @create 2019-08-30 15:56
 */
@Configuration
@Slf4j
@PropertySource ("classpath:application-redis.yml")
public class RedisConfig {

    @Value ("${host}")
    private String host;
    @Value ("${port}")
    private int port;
    @Value ("${timeout}")
    private int timeout;
    @Value ("${max-idle}")
    private int maxIdle;
    @Value ("${max-wait}")
    private long maxWaitMillis;
    @Value ("${password}")
    private String password;
    @Value ("${block-when-exhausted}")
    private boolean blockWhenExhausted;

    @Bean
    public JedisPool redisPoolFactory() {
        log.info("JedisPool注入成功！！");
        log.info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
        return jedisPool;
    }
}