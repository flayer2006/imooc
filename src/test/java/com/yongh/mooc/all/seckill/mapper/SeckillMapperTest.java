package com.yongh.mooc.all.seckill.mapper;

import com.yongh.mooc.all.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillMapperTest {

    @Autowired
    private SeckillMapper seckillMapper;

    @Test
    public void testSelectByPrimaryKey(){
        long id = 1000;
        Seckill seckill = seckillMapper.selectByPrimaryKey (id);
        System.out.println (seckill.getName ());
    }

    @Test
    public void testSelectAllByParams(){
        List<Seckill> list = seckillMapper.selectAllByParams (0,100);
        for (Seckill seckill:list){
            System.out.println (seckill);
        }
    }


    @Test
    public void testReduceNumber(){
        Date date = new Date();

        int updateCount = seckillMapper.reduceNumber(1000L,date);
        System.out.println("updateCount = " + updateCount);
    }
}