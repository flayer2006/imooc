package com.yongh.mooc.all.seckill.exception;

/**
 * 描述: 重复秒杀异常
 *
 * @author soitis
 * @create 2019-08-30 17:31
 */
public class RepeatKillException extends SeckillException{

    public RepeatKillException(String message, Throwable cause) {
        super (message, cause);
    }

    public RepeatKillException(String message) {
        super (message);
    }
}