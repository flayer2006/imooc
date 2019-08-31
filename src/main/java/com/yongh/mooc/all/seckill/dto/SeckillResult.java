package com.yongh.mooc.all.seckill.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 描述:
 *
 * @author soitis
 * @create 2019-08-30 17:56
 */
@ToString
@Data
public class SeckillResult<T> {
    //错误代码，状态值：0 ：成功，其他数值代表失败
    private Boolean success;
    //结果集
    private T data;
    //错误信息
    private String error;

    public SeckillResult(Boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SeckillResult(Boolean success, String error) {
        this.success = success;
        this.error = error;
    }
}