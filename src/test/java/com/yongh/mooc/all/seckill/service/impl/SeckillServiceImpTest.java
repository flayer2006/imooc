package com.yongh.mooc.all.seckill.service.impl;

import com.yongh.mooc.all.seckill.core.BaseController;
import com.yongh.mooc.all.seckill.dto.Exposer;
import com.yongh.mooc.all.seckill.dto.SeckillExecution;
import com.yongh.mooc.all.seckill.entity.Seckill;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith (SpringRunner.class)
@SpringBootTest
@Slf4j
public class SeckillServiceImpTest extends BaseController {

    @Test
    public void getList() {
        List<Seckill> list = seckillService.getList ();
        log.info ("list:{}",list);
    }

    @Test
    public void getById() {
        long id = 1000L;
        Seckill seckill = seckillService.getById (id);
        log.info ("seckill:{}",seckill);
    }

    @Test
    public void exportSeckillUrl(){
        long id = 1001L;
        Exposer exposer = seckillService.exportSeckillUrlUseRedis (id);
        log.info ("exposer:{}",exposer);
    }

    @Test
    public void executeSeckill(){
        long seckillId = 1001L;
        long phone = 12345678911L;
        Exposer exposer = seckillService.exportSeckillUrlUseRedis (seckillId);
        if(exposer.getState ()){
            String md5 = exposer.getMd5 ();
            SeckillExecution execution = seckillService.executeSeckill (seckillId,phone,md5);
            log.info (execution.getStateDesc ());
        }
    }
}
