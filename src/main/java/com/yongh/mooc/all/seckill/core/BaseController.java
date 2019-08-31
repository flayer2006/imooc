package com.yongh.mooc.all.seckill.core;

import com.yongh.mooc.all.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述: Controller 的基类，用于抽取注入的Service
 *
 * @author soitis
 * @create 2019-08-29 15:41
 */
public abstract class BaseController {
    @Autowired
    protected SeckillService seckillService;
}