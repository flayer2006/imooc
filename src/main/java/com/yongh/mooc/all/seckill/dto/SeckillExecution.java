package com.yongh.mooc.all.seckill.dto;

import com.yongh.mooc.all.seckill.entity.SuccessKilled;
import com.yongh.mooc.all.seckill.enums.SeckillStateEnum;
import lombok.Data;
import lombok.ToString;

/**
 * 描述: 封装秒杀后的结果
 *
 * @author soitis
 * @create 2019-08-30 8:16
 */
@ToString
@Data
public class SeckillExecution {
    // 库存商品id
    private Long seckillId;
    //秒杀结果状态
    private Integer state;
    //状态标识
    private String stateDesc;
    //秒杀成功对象
    private SuccessKilled successKilled;

    public SeckillExecution(Long seckillId, SeckillStateEnum seckillStateEnum) {
        this.seckillId = seckillId;
        this.state = seckillStateEnum.getCode();
        this.stateDesc = seckillStateEnum.getMsg ();
    }

    public SeckillExecution(Long seckillId, SeckillStateEnum seckillStateEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = seckillStateEnum.getCode ();
        this.stateDesc = seckillStateEnum.getMsg ();
        this.successKilled = successKilled;
    }

}