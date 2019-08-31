package com.yongh.mooc.all.seckill.service;

import com.yongh.mooc.all.seckill.dto.Exposer;
import com.yongh.mooc.all.seckill.dto.SeckillExecution;
import com.yongh.mooc.all.seckill.entity.Seckill;

import java.util.List;

public interface SeckillService {

    //查询所有秒杀列表
    List<Seckill> getList();
    //查询单个秒杀记录
    Seckill getById(Long seckillId);
    //输出秒杀接口的地址，秒杀开启时输出接口地址，否则输出秒杀时间和系统时间
    Exposer exportSeckillUrl(Long seckillId);
    //采用redis的输出秒杀接口的地址操作
    Exposer exportSeckillUrlUseRedis(Long seckillId);
    //执行秒杀操作,匹配md5是否一致，判断用户秒杀地址是否正常
    SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5);
    //通过存储过程执行秒杀操作
    SeckillExecution executeSeckillProcedur(Long seckillId, Long userPhone, String md5);
}
