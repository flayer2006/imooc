package com.yongh.mooc.all.seckill.exception;

/**
 * 描述:
 * 秒杀关闭异常
 *
 * @author soitis
 * @create 2019-08-30 17:35
 */
public class SeckillCloseException extends SeckillException{

    public SeckillCloseException(String message, Throwable cause) {
        super (message, cause);
    }

    public SeckillCloseException(String message) {
        super (message);
    }
}