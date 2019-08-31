package com.yongh.mooc.all.seckill.utils;

import com.yongh.mooc.all.seckill.core.BaseController;
import com.yongh.mooc.all.seckill.entity.Seckill;
import com.yongh.mooc.all.seckill.mapper.SeckillMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith (SpringRunner.class)
@SpringBootTest
public class RedisUtilTest extends BaseController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    protected SeckillMapper seckillMapper;

    @Test
    public void putSeckill() {
        long id = 1001L;

        Seckill seckill = redisUtil.getSeckill (id);
        if(seckill == null){
            seckill = seckillMapper.selectByPrimaryKey (id);
            if(seckill != null){
                String result = redisUtil.putSeckill (seckill);
                System.out.println (result);
                seckill = redisUtil.getSeckill (id);
                System.out.println (seckill);
            }
        }

    }

}