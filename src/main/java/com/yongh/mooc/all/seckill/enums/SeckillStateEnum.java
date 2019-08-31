package com.yongh.mooc.all.seckill.enums;

/**
 * 描述: 使用枚举表述常量数据字段
 *
 * @author soitis
 * @create 2019-08-30 9:32
 */

public enum SeckillStateEnum {

    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"你已秒杀成功，不能重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改"),
    UNKNOWN_ERROR(500,"未知错误");

    private Integer code;
    private String msg;

    SeckillStateEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static SeckillStateEnum stateOf(int result){
        for(SeckillStateEnum stateEnum:values ()){
            if(stateEnum.getCode () == result){
                return stateEnum;
            }
        }
        return null;
    }
}