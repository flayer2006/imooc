package com.yongh.mooc.all.seckill.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 描述:
 * 是否暴露秒杀地址，否则提示时间
 *
 * @author soitis
 * @create 2019-08-29 18:28
 */
@ToString
@Data
public class Exposer {

    //秒杀是否可以开始，地址是否可以暴露
    private Boolean state;
    //加密措施
    private String md5;

    private Long seckillId;

    //系统当前时间，与start和end对比 （毫秒）
    private Long now;
    // 秒杀开始时间
    private Long start;
    //秒杀结束时间
    private Long end;

    public Exposer(Boolean state, Long seckillId) {
        this.state = state;
        this.seckillId = seckillId;
    }

    public Exposer(Boolean state, Long seckillId, Long now, Long start, Long end) {
        this.state = state;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(Boolean state, String md5, Long seckillId) {
        this.state = state;
        this.md5 = md5;
        this.seckillId = seckillId;
    }
}