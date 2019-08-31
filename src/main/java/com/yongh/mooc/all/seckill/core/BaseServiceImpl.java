package com.yongh.mooc.all.seckill.core;

/**
 * 描述: ServiceImpl层基础类，用于抽取注入的Mapper
 *
 * @author soitis
 * @create 2019-08-29 15:06
 */

import com.yongh.mooc.all.seckill.mapper.SeckillMapper;
import com.yongh.mooc.all.seckill.mapper.SuccessKilledMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseServiceImpl {

    @Autowired
    protected SeckillMapper seckillMapper;

    @Autowired
    protected SuccessKilledMapper successKilledMapper;
}