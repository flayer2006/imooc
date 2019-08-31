package com.yongh.mooc.all.seckill.exception;

/**
 * 描述: 秒杀相关异常
 *
 * @author soitis
 * @create 2019-08-30 11:26
 */
public class SeckillException extends RuntimeException{

    public SeckillException(String message, Throwable cause) {
        super (message, cause);
    }

    public SeckillException(String message) {

        super (message);
    }
}